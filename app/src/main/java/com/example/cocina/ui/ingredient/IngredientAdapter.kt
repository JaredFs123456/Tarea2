package com.example.cocina.ui.ingredient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load                         // ← usa Coil (ya lo tienes en deps)
import com.example.cocina.data.model.Ingredient
import com.example.cocina.databinding.ItemIngredientBinding

class IngredientAdapter(
    private val onInfo: (Ingredient) -> Unit
) : ListAdapter<Ingredient, IngredientAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Ingredient>() {
            override fun areItemsTheSame(o: Ingredient, n: Ingredient) = o.id == n.id
            override fun areContentsTheSame(o: Ingredient, n: Ingredient) = o == n
        }
    }

    inner class VH(val b: ItemIngredientBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: Ingredient) = with(b) {
            tvQty.text = item.qty
            tvName.text = item.name

            // Imagen opcional (si añadiste "image" al modelo y "ivIcon" al layout)
            val resId = if (!item.image.isNullOrBlank())
                root.context.resources.getIdentifier(item.image, "drawable", root.context.packageName)
            else 0
            ivIcon.isVisible = resId != 0
            if (resId != 0) ivIcon.load(resId) { crossfade(true) } else ivIcon.setImageDrawable(null)

            // POI: botón solo si hay nota/sustitución (limpiar listeners por reciclado)
            btnInfo.setOnClickListener(null)
            root.setOnClickListener(null)
            val hasNote = !item.note.isNullOrBlank()
            btnInfo.isVisible = hasNote
            if (hasNote) {
                btnInfo.setOnClickListener { onInfo(item) }
                root.setOnClickListener { onInfo(item) } // abrir también tocando la tarjeta
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))
}
