package com.giac.restauranttrends.view.ui.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.giac.restauranttrends.R
import com.giac.restauranttrends.databinding.CollectionItemLayoutBinding
import com.giac.restauranttrends.model.entity.Collection

class CollectionListAdapter(
    private val callback : CollectionItemCallback
) : RecyclerView.Adapter<CollectionListAdapter.CollectionItemViewHolder>() {

    private var collectionList: List<Collection> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionItemViewHolder {
        val binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.collection_item_layout, parent, false) as CollectionItemLayoutBinding
        binding.root.setOnClickListener {
            binding.collection?.let {
                callback.onClick(it)
            }
        }
        return CollectionItemViewHolder(binding)
    }

    override fun getItemCount(): Int = collectionList.size

    override fun onBindViewHolder(holder: CollectionItemViewHolder, position: Int) {
        val collection = collectionList[position]
        holder.bind(collection)
    }

    fun setData(collectionList: List<Collection>) {
        this.collectionList = collectionList
        notifyDataSetChanged()
    }

    inner class CollectionItemViewHolder(var binding: CollectionItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(collection: Collection) {
            binding.collection = collection
            binding.executePendingBindings()
        }
    }
}