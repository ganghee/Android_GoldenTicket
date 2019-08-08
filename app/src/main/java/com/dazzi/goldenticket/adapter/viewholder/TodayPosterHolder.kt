package com.dazzi.goldenticket.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dazzi.goldenticket.R

class TodayPosterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val container = itemView.findViewById(R.id.cv_main_poster) as CardView
    val poster = itemView.findViewById(R.id.iv_main_poster) as ImageView
    val showName = itemView.findViewById(R.id.tv_rv_item_name) as TextView
    val location = itemView.findViewById(R.id.tv_rv_item_location) as TextView
    val time = itemView.findViewById(R.id.tv_rv_item_time) as TextView

}