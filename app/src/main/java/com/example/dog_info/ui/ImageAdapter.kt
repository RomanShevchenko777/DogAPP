package com.example.dog_info.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dog_info.GlideApp
import com.example.dog_info.R
import com.example.dog_info.databinding.ItemImageBinding

class ImageAdapter(
    private val imageLinks: List<String> ? = null,
    private val itemAction: ItemAction
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemImageBinding.bind(view)
        var itemAction: ItemAction ? = null

        fun bind(imageLink: String) = binding.apply {

            GlideApp.with(imageItem.context)
                .load(imageLink)
                .placeholder(R.drawable.android)
                .into(imageItem)

            itemView.setOnClickListener{
                itemAction?.onItemListener(imageLink)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        imageLinks?.get(position)?.let {
            holder.bind(
                it
            )
        }
        holder.itemAction = this@ImageAdapter.itemAction
    }

    override fun getItemCount(): Int = imageLinks?.size ?: 0

    interface ItemAction {
        fun onItemListener(imageLink: String)
    }

}