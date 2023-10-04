package com.zahra.recyclerviewmultipleselection.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zahra.recyclerviewmultipleselection.R
import com.zahra.recyclerviewmultipleselection.adapter.item.ItemData
import com.zahra.recyclerviewmultipleselection.adapter.item.ItemViewHolder

class Adapter : RecyclerView.Adapter<ItemViewHolder>() {

    private var list: MutableList<ItemData> = mutableListOf()

    var onLoadMore: (() -> Unit)? = null

    private var isSelectionMode: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.data = list[position]
        holder.isSelectionMode = isSelectionMode
        holder.onSelection = {
            for (data in list) {
                if (data.isSelected) {
                    Log.d("???", "selected data is ${data.content} ${data.id} ")
                }
            }
        }

        holder.updateView()
        if (position == list.size - 1) {
            onLoadMore?.let {
                it()
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun reload(list: MutableList<ItemData>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()

    }

    fun loadMore(list: MutableList<ItemData>) {
        this.list.addAll(list)
        notifyItemRangeChanged(this.list.size - list.size + 1, list.size)
    }

    fun toggleSelectionMode(isSelectionMode: Boolean) {
        this.isSelectionMode = isSelectionMode
        notifyDataSetChanged()

    }
}