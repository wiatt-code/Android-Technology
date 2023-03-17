package com.wiatt.simpledemo.myViewPager2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.wiatt.simpledemo.R

class ViewPager2Adapter(var students: MutableList<Fragment>): RecyclerView.Adapter<ViewPager2Adapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_blank, parent, false))
    }

    override fun getItemCount(): Int {
        return students.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}