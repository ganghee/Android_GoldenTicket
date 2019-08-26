package com.dazzi.goldenticket.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.data.StageInfoImgsData
import org.jetbrains.anko.matchParent

class StageInfoImgsRVAdpater(val ctx: Context, var dataList: List<StageInfoImgsData>) :
    RecyclerView.Adapter<StageInfoImgsRVAdpater.Holder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_stageinfo_imgs, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {

        Log.d("#####", " " + dataList.size)
        return dataList.size
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {

        Log.d("###onBindViewHolder##", " " + dataList[position].imageUrl)
        Glide.with(ctx)
            .load(dataList[position].imageUrl)
            .placeholder(R.drawable.poster_main_benhur)
            .apply(RequestOptions.centerCropTransform())
            .override(1200,1921)
            .fitCenter()
            .into(holder.infoImgUrl)
            //.waitForLayout()
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var infoImgUrl = itemView.findViewById(R.id.iv_stageinfo_info_img) as ImageView
    }
}