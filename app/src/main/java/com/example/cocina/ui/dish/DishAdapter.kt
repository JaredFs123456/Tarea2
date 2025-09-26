package com.example.cocina.ui.dish

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cocina.R
import com.example.cocina.data.model.Dish
import com.example.cocina.databinding.ItemDishBinding

class DishAdapter(
    private val onClick: (Dish, ImageView) -> Unit,
    private val onInfo: (Dish) -> Unit
) : ListAdapter<Dish, DishAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Dish>() {
            override fun areItemsTheSame(oldItem: Dish, newItem: Dish) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Dish, newItem: Dish) = oldItem == newItem
        }
    }

    inner class VH(val b: ItemDishBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: Dish) = with(b) {
            tvName.text = item.name

            // transitionName único por item
            ivCover.transitionName = "dishImage_${item.id}"

            // Cargar imagen por nombre de drawable; usar placeholder si no existe
            val resId = root.context.resources.getIdentifier(item.image, "drawable", root.context.packageName)
            if (resId != 0) {
                ivCover.load(resId)
            } else {
                ivCover.setImageResource(R.drawable.ic_launcher_foreground)
            }

            root.setOnClickListener { onClick(item, ivCover) }
            btnInfo.setOnClickListener { onInfo(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inf = LayoutInflater.from(parent.context)
        return VH(ItemDishBinding.inflate(inf, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}
