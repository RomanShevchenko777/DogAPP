package com.example.dog_info.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dog_info.R
import com.example.dog_info.databinding.ItemBreedBinding
import com.example.dog_info.ui.viewmodel.BreedItemViewModel

class BreedListAdapter(
    private val itemAction: ItemAction,
    private val breedList: MutableList<String>
) : RecyclerView.Adapter<BreedListAdapter.BreedViewHolder>() {

    class BreedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemBreedBinding.bind(view)
        var itemAction: ItemAction? = null

        fun bind(breed: String) = binding.apply {
            viewModel = BreedItemViewModel().apply {
                bind(breed)
            }

            itemView.setOnClickListener {
                itemAction?.onItemListener(breed)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_breed, parent, false)
        return BreedViewHolder(view)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(breedList[position])
        holder.itemAction = this@BreedListAdapter.itemAction
    }

    fun addItems(items: List<String>) {
        breedList.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = breedList.size

    interface ItemAction {
        fun onItemListener(dogEntity: String)
    }
}