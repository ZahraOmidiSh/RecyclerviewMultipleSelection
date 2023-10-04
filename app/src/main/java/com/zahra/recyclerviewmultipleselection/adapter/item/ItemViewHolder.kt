package com.zahra.recyclerviewmultipleselection.adapter.item

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.zahra.recyclerviewmultipleselection.R
import java.lang.ref.WeakReference

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var view: WeakReference<View> = WeakReference(itemView)

    private var textView: TextView? = null
    private var imageButtonSelection: ImageButton? = null

    lateinit var data: ItemData
    var isSelectionMode = false
    var onSelection: (() -> Unit?)? = null

    init {
        findView()
        setListener()
    }

    private fun findView() {
        textView = view.get()?.findViewById(R.id.textView)
        imageButtonSelection = view.get()?.findViewById(R.id.imageButtonSelection)
    }

    private fun setListener() {
        view.get()?.setOnClickListener {
            if (isSelectionMode) {

                data.isSelected = !data.isSelected
                updateSelection()

                onSelection?.let {
                    it()
                }

            } else {
                Toast.makeText(it.context, data.content, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun updateView() {
        textView?.text = "${data.content}\n\n${data.id}"
        imageButtonSelection?.visibility = if (isSelectionMode) {
            View.VISIBLE
        } else {
            View.GONE
        }
        if (isSelectionMode) {
            //clear selected data when isSelectedMode is false
            data.isSelected = false
        }
        updateSelection()
    }

    private fun updateSelection() {
        imageButtonSelection?.setImageResource(
            if (data.isSelected) {
                R.drawable.checked
            } else {
                R.drawable.unchecked
            }
        )
    }

}
