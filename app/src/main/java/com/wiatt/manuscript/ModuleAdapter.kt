package com.wiatt.manuscript

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter

class ModuleAdapter(var context: Context, var infos: MutableList<ModuleInfo>): RecyclerView.Adapter<ModuleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View = LayoutInflater.from(context)
            .inflate(R.layout.item_app_entrance, parent, false)
        var viewHolder: ViewHolder = ViewHolder(itemView)
        viewHolder.btnEntrance.setOnClickListener {
            ARouter.getInstance()
                .build(infos[viewHolder.layoutPosition].path)
                .navigation()
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.btnEntrance.text = infos[position].name
    }

    override fun getItemCount(): Int {
        return infos.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var btnEntrance: Button = itemView.findViewById(R.id.btnEntrance)
    }
}