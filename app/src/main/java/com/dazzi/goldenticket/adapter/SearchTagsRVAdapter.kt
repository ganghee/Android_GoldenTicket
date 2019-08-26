package com.dazzi.goldenticket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.dazzi.goldenticket.R


class SearchTagsRVAdapter(val ctx: Context, val dataList: ArrayList<String>): RecyclerView.Adapter<SearchTagsRVAdapter.Holder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_search_tags, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvSearchTag.text = dataList[position]
        holder.tvSearchTag.marginBottom
        holder.tvSearchTag.setOnClickListener {
            var keyword = holder.tvSearchTag.text
            //keyword로 검색, 결과값 SearchResultActivity로 전달
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvSearchTag = itemView.findViewById(R.id.tv_search_tag) as TextView
    }
}
