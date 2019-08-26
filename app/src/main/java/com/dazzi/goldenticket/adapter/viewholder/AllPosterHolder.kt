package com.dazzi.goldenticket.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.data.ShowAllData
import kotlinx.android.synthetic.main.rv_item_show_all.view.*

class AllPosterHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
    private val mainPosterImage = itemView.ivMainPoster
    val container = itemView.cvAllPoster

    fun bind(allPoster: ShowAllData){
        Glide.with(itemView)
            .load(allPoster.imageUrl)
            .into(mainPosterImage)

    }
}