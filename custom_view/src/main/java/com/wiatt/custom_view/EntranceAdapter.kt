package com.wiatt.custom_view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class EntranceAdapter(var context: Context, var infos: MutableList<EntranceInfo>)
    : RecyclerView.Adapter<EntranceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View = LayoutInflater.from(context).inflate(R.layout.item_customview_entrance, parent, false)
        var viewHolder = ViewHolder(itemView)
        viewHolder.btnEntrance.setOnClickListener {
            context.startActivity(
                Intent(context, infos[viewHolder.layoutPosition].activity::class.java)
            )
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