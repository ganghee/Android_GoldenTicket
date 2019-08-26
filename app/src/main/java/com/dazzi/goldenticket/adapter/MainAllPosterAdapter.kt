package com.dazzi.goldenticket.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.adapter.viewholder.AllPosterHolder
import com.dazzi.goldenticket.data.ShowAllData

class MainAllPosterAdapter : RecyclerView.Adapter<AllPosterHolder>() {

    private val allPosterList = mutableListOf<ShowAllData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AllPosterHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_item_show_all,
                parent,
                false
            )
        )

    override fun getItemCount() = allPosterList.size

    override fun onBindViewHolder(holder: AllPosterHolder, position: Int) {
        holder.bind(allPosterList[position])

    }

    fun setData(setAllDataList: List<ShowAllData>) {
        this.allPosterList.clear()
        this.allPosterList.addAll(setAllDataList)
        notifyDataSetChanged()
    }

}