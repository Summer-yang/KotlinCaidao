package com.summer.base.library.demo.caidao

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
                      private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_menu_list, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
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

    private inner class ItemViewHolder(item: View) : RecyclerView.ViewHolder(item)

    interface OnItemClickListener {
        fun itemClick(view: View, position: Int)
    }

}