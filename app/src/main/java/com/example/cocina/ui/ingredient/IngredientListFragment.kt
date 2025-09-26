package com.example.cocina.ui.ingredient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocina.data.repository.AssetRepository
import com.example.cocina.databinding.FragmentIngredientListBinding
import com.example.cocina.ui.info.InfoBottomSheet

class IngredientListFragment : Fragment() {

    private var _b: FragmentIngredientListBinding? = null
    private val b get() = _b!!

    private lateinit var repo: AssetRepository
    private lateinit var adapter: IngredientAdapter

    private var subprepId = ""
    private var dishName = ""
    private var subprepName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subprepId = it.getString(ARG_SUBPREP_ID).orEmpty()
            dishName = it.getString(ARG_DISH_NAME).orEmpty()
            subprepName = it.getString(ARG_SUBPREP_NAME).orEmpty()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentIngredientListBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repo = AssetRepository(requireContext())

        b.tvHeader.text = "$dishName • $subprepName"

        adapter = IngredientAdapter(
            onInfo = { ing ->
                val note = ing.note ?: "Sin notas para este ingrediente."
                InfoBottomSheet.newInstance(
                    title = "Nota: ${ing.name}",
                    message = note,
                    imageName = null
                ).show(parentFragmentManager, "ing_note")
            }
        )

        b.recycler.layoutManager = LinearLayoutManager(requireContext())
        b.recycler.adapter = adapter

        adapter.submitList(repo.getIngredients(subprepId))
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }

    companion object {
        private const val ARG_SUBPREP_ID = "subprepId"
        private const val ARG_DISH_NAME = "dishName"
        private const val ARG_SUBPREP_NAME = "subprepName"

        fun newInstance(subprepId: String, dishName: String, subprepName: String) =
            IngredientListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SUBPREP_ID, subprepId)
                    putString(ARG_DISH_NAME, dishName)
                    putString(ARG_SUBPREP_NAME, subprepName)
                }
            }
    }
}
