package com.example.cocina.ui.subprep

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import coil.load
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cocina.data.model.Subprep
import com.example.cocina.databinding.ItemSubprepBinding

class SubprepAdapter(
    private val onClick: (Subprep) -> Unit,
    private val onTip: (Subprep) -> Unit
) : ListAdapter<Subprep, SubprepAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Subprep>() {
            override fun areItemsTheSame(oldItem: Subprep, newItem: Subprep) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Subprep, newItem: Subprep) = oldItem == newItem
        }
    }

    inner class VH(val b: ItemSubprepBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: Subprep) = with(b) {
            tvName.text = item.name
            chipKind.text = item.kind
            tvDesc.text = item.description

            val resId = if (!item.mediaUrl.isNullOrBlank())
                root.context.resources.getIdentifier(item.mediaUrl, "drawable", root.context.packageName)
            else 0

            if (resId != 0) {
                ivThumb.visibility = View.VISIBLE
                ivThumb.load(resId)
            } else {
                ivThumb.visibility = View.GONE
            }
            // estilo sencillo para distinguir "info"
            chipKind.isCheckable = false
            chipKind.isClickable = false

            root.setOnClickListener { onClick(item) }
            btnTip.setOnClickListener { onTip(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inf = LayoutInflater.from(parent.context)
        return VH(ItemSubprepBinding.inflate(inf, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

}
