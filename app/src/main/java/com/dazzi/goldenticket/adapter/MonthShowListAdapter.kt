package com.dazzi.goldenticket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.activity.ContentActivity
import com.dazzi.goldenticket.data.CardListData
import com.dazzi.goldenticket.R
import kotlinx.android.synthetic.main.rv_main_contents_item.view.*

class CardListAdapter: RecyclerView.Adapter<CardListAdapter.Holder>() {

    val monthShowList = mutableListOf<CardListData>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): Holder =
        MonthShowHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_main_contents_item,viewGroup,false))


    override fun getItemCount() = monthShowList.size


    override fun onBindViewHolder(holder: Holder, position: Int)

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cvMonth = itemView.cvMonth
        val title  = itemView.findViewById(R.id.tvTitle) as TextView
        val category  = itemView.findViewById(R.id.tvCategory) as TextView
        val img = itemView.findViewById(R.id.ivCard) as ImageView
    }
}