package com.summer.base.library.demo.caidao.share

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.summer.base.library.R
import kotlinx.android.synthetic.main.item_share.view.*

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/15
 *
 * Description:
 *
 */
class AdapterShare(private val icons: Array<Int>, private val onShareClickListener: OnShareClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return icons.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return IconView(LayoutInflater.from(parent.context).inflate(R.layout.item_share, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is IconView) {
            holder.itemView.apply {
                ivIcon.tag = position
                ivIcon.setOnClickListener {
                    onShareClickListener.onShareItemClick(it.tag as Int)
                }
                ivIcon.setImageResource(icons[position])
            }
        }
    }

    private class IconView(item: View) : RecyclerView.ViewHolder(item)

    interface OnShareClickListener {
        fun onShareItemClick(position: Int)
    }

}