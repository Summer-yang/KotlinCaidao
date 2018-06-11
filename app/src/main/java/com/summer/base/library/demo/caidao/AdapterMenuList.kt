package com.summer.base.library.demo.caidao

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.base.library.R
import kotlinx.android.synthetic.main.item_menu_list.view.*

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/11
 *
 * Description:
 *
 */
class AdapterMenuList(private val items: Array<String>,
                      private val onItemClickListener: OnItemClickListener) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_menu_list, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            with(holder.itemView) {

                clRootView.tag = position
                clRootView.setOnClickListener {
                    onItemClickListener.itemClick(it, it.tag as Int)
                }

                tvTitle.text = items[position]

            }
        }
    }

    private inner class ItemViewHolder(item: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(item)

    interface OnItemClickListener {
        fun itemClick(view: View, position: Int)
    }

}