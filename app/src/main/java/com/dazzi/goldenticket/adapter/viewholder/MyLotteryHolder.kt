package com.dazzi.goldenticket.adapter.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.activity.MyLotteryDetailActivity
import com.dazzi.goldenticket.data.MyLotteryData
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class MyLotteryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val stageInfoPosterUrl = itemView.findViewById(R.id.iv_mylottery_poster) as ImageView
    private val stageInfoDate = itemView.findViewById(R.id.tv_mylottery_date) as TextView
    private val stageInfoTitle = itemView.findViewById(R.id.tv_mylottery_title) as TextView
    private val stageInfoDetail = itemView.findViewById(R.id.tv_mylottery_detail) as TextView
    private val stageInfoLocation = itemView.findViewById(R.id.tv_mylottery_location) as TextView
    private val stageInfoTime = itemView.findViewById(R.id.tv_mylottery_time) as TextView
    private val stageInfoContent = itemView.findViewById(R.id.rlMyLotteryTicket) as RelativeLayout

    fun bind(lotteryData: MyLotteryData) {
        Glide.with(itemView)
            .load(lotteryData.imageUrl)
            .into(stageInfoPosterUrl)
        stageInfoDate.text = lotteryData.date
        stageInfoTitle.text = lotteryData.name
        stageInfoDetail.text = lotteryData.seatName
        stageInfoLocation.text = lotteryData.location
        stageInfoTime.text = lotteryData.runningTime

        stageInfoContent.onClick {
            Log.e(
                "*****MyLottRVAdapter::",
                "onCreateViewHolder::setOnClickListener::idx" + lotteryData.ticketIdx
            )

            itemView.context.startActivity<MyLotteryDetailActivity>("idx" to lotteryData.ticketIdx) //ticket_idx
        }
    }
}