package com.wiatt.custom_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class ViewAdapter(var context: Context, var infos: MutableList<ViewInfo>)
    : RecyclerView.Adapter<ViewAdapter.ViewHolder>() {

    var callback: CustomViewFragment.CustomViewCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View = LayoutInflater.from(context).inflate(R.layout.item_view_button, parent, false)
        var viewHolder = ViewHolder(itemView)
        viewHolder.btnSelect.setOnClickListener {
            callback?.onClickItem(infos[viewHolder.layoutPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.btnSelect.text = infos[position].name
    }

    override fun getItemCount(): Int {
        return infos.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var btnSelect: Button = itemView.findViewById(R.id.btnSelect)
    }

    interface ViewAdapterCallback {
        fun onClickItem(info: ViewInfo)
    }
}