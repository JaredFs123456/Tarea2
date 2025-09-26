package com.example.cocina.ui.subprep

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.cocina.data.model.Subprep
import com.example.cocina.data.repository.AssetRepository
import com.example.cocina.databinding.FragmentSubprepListBinding
import com.example.cocina.ui.info.InfoBottomSheet
import com.example.cocina.ui.ingredient.IngredientActivity

class SubprepListFragment : Fragment() {

    private var _b: FragmentSubprepListBinding? = null
    private val b get() = _b!!

    private lateinit var repo: AssetRepository
    private lateinit var adapter: SubprepAdapter
    private var dishId: String = ""
    private var sharedName: String? = null       // ★

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dishId = it.getString(ARG_DISH_ID).orEmpty()
            sharedName = it.getString(ARG_SHARED_NAME)       // ★
        }
        postponeEnterTransition()                            // ★ espera la imagen antes de animar
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _b = FragmentSubprepListBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repo = AssetRepository(requireContext())

        val dish = repo.getDishById(dishId)
        b.tvDishName.text = dish?.name.orEmpty()

        // Imagen de cabecera + transitionName
        val resId = resources.getIdentifier(dish?.image ?: "", "drawable", requireContext().packageName)
        b.ivHeader.transitionName = sharedName ?: "dishImage_${dishId}"    // ★ mismo nombre que en el nivel 1
        if (resId != 0) {
            b.ivHeader.load(resId) {
                listener(
                    onSuccess = { _, _ -> startPostponedEnterTransition() },  // ★ inicia animación cuando cargue
                    onError = { _, _ -> startPostponedEnterTransition() }
                )
            }
        } else {
            startPostponedEnterTransition()
        }

        adapter = SubprepAdapter(
            onClick = { sp ->
                if (sp.kind == "info") {
                    InfoBottomSheet.newInstance(sp.name, sp.description, dish?.image)
                        .show(parentFragmentManager, "info")
                } else {
                    // Nivel 3
                    startActivity(
                        Intent(requireContext(), IngredientActivity::class.java)
                            .putExtra("subprepId", sp.id)
                            .putExtra("dishName", dish?.name ?: "")
                            .putExtra("subprepName", sp.name)
                    )
                    requireActivity().overridePendingTransition(       // ★ animación 2→3
                        com.example.cocina.R.anim.slide_in_right,
                        com.example.cocina.R.anim.fade_out
                    )
                }
            },
            onTip = { sp -> showTips(sp) }
        )

        b.recycler.layoutManager = LinearLayoutManager(requireContext())
        b.recycler.adapter = adapter

        val ordered = repo.getSubpreps(dishId).sortedBy { it.order }
        adapter.submitList(ordered)
    }

    private fun showTips(sp: Subprep) {
        val message = if (sp.tips.isEmpty()) "Sin tips registrados."
        else "• " + sp.tips.joinToString("\n• ")
        InfoBottomSheet.newInstance(title = "Tips: ${sp.name}", message = message, imageName = null)
            .show(parentFragmentManager, "tips")
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }

    companion object {
        private const val ARG_DISH_ID = "dishId"
        private const val ARG_SHARED_NAME = "sharedName"    // ★

        fun newInstance(dishId: String, sharedName: String?) = SubprepListFragment().apply { // ★
            arguments = Bundle().apply {
                putString(ARG_DISH_ID, dishId)
                putString(ARG_SHARED_NAME, sharedName)
            }
        }
    }
}
