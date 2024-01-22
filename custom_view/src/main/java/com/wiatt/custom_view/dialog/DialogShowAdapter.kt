package com.wiatt.custom_view.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.wiatt.custom_view.R

/**
 * @author wiatt
 * @description:
 */
class DialogShowAdapter(var context: Context, var dialogInfos: MutableList<String>)
    : RecyclerView.Adapter<DialogShowAdapter.DialogShowViewHolder>(){

    var callback: DialogShowFragment.DialogShowCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogShowViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.item_dialog_show, parent, false)
        val viewHolder = DialogShowViewHolder(itemView)
        viewHolder.btnSelect.setOnClickListener {
            callback?.onClickItem(dialogInfos[viewHolder.layoutPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return dialogInfos.size
    }

    override fun onBindViewHolder(holder: DialogShowViewHolder, position: Int) {
        holder.btnSelect.text = dialogInfos[position]
    }

    inner class DialogShowViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var btnSelect: Button = itemView.findViewById(R.id.btnDialog)
    }

    interface IDialogShowAdapterCallback {
        fun onClickItem(info: String)
    }
}