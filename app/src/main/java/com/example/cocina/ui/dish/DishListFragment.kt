package com.example.cocina.ui.dish

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocina.data.model.Dish
import com.example.cocina.data.repository.AssetRepository
import com.example.cocina.databinding.FragmentDishListBinding
import com.example.cocina.ui.info.InfoBottomSheet
import com.example.cocina.ui.subprep.SubprepActivity

class DishListFragment : Fragment() {

    private var _binding: FragmentDishListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repo: AssetRepository
    private lateinit var adapter: DishAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repo = AssetRepository(requireContext())

        adapter = DishAdapter(
            onClick = { dish, imageView ->

                val intent = Intent(requireContext(), SubprepActivity::class.java)
                    .putExtra("dishId", dish.id)

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(), imageView, imageView.transitionName
                )
                startActivity(intent, options.toBundle())
                adapter = DishAdapter(
                    onClick = { selectedDish, coverView ->
                        val navIntent = Intent(requireContext(), SubprepActivity::class.java)
                            .putExtra("dishId", selectedDish.id)
                            .putExtra("sharedName", coverView.transitionName)

                        val opts = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            requireActivity(), coverView, coverView.transitionName
                        )
                        startActivity(navIntent, opts.toBundle())
                    },
                    onInfo = { selectedDish -> showInfo(selectedDish) }
                )

            },
            onInfo = { dish -> showInfo(dish) }
        )

        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recycler.adapter = adapter

        // Carga de datos
        val dishes = repo.getDishes()
        adapter.submitList(dishes)

        // scroll al top al entrar
        binding.recycler.doOnNextLayout { binding.recycler.scrollToPosition(0) }
    }

    private fun showInfo(dish: Dish) {
        val text = dish.origin.ifBlank { dish.summary }
        InfoBottomSheet.newInstance(
            title = dish.name,
            message = text,
            imageName = dish.image
        ).show(parentFragmentManager, "info")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
