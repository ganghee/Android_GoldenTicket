package com.dazzi.goldenticket.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dazzi.goldenticket.data.StageInfoActorsData
import com.dazzi.goldenticket.R

class StageInfoActorsRVAdapter(val ctx: Context, var dataList: List<StageInfoActorsData>): RecyclerView.Adapter<StageInfoActorsRVAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_stageinfo_actors, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //로딩 느려질 시, var options: RequestOptions = RequestOptions().placeholder(R.drawable.placeholder), .apply(options), 이미지 바꾸기
        Log.e("InfoActorsRVAdptr::: ", "onBindViewHolder::actors:: " + dataList[position].image_url)


        Glide.with(ctx)
            .load(dataList[position].image_url)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.actorImgUrl)

        holder.actorName.text = dataList[position].name
        holder.actorRole.text = dataList[position].role

    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var actorImgUrl = itemView.findViewById(R.id.iv_stageinfo_actor_img) as ImageView
        var actorName = itemView.findViewById(R.id.tv_stageinfo_actor_name) as TextView
        var actorRole = itemView.findViewById(R.id.tv_stageinfo_actor_role) as TextView

    }
}