package com.dazzi.goldenticket.adapter.viewholder

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.activity.StageInfoActivity
import com.dazzi.goldenticket.data.MonthShowDetailContentData
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class MonthShowDetailHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val contentSubtitle = itemView.findViewById(R.id.tvContentItemSubTitle) as TextView
    private val contentTitle = itemView.findViewById(R.id.tvContentItemTitle) as TextView
    private val contentImage = itemView.findViewById(R.id.ivContentItemImage) as ImageView
    private val contentContent = itemView.findViewById(R.id.tvContentItemContent) as TextView
    private val contentGoShow = itemView.findViewById(R.id.btnGoShowInfo) as Button

    fun bind(monthShowDetailContentData: MonthShowDetailContentData) {

        contentSubtitle.text = monthShowDetailContentData.subtitle
        contentTitle.text = monthShowDetailContentData.title

        Glide.with(itemView)
            .load(monthShowDetailContentData.imageUrl)
            .into(contentImage)

        monthShowDetailContentData.content = monthShowDetailContentData.content.replace("/r", "\n")
        monthShowDetailContentData.content = monthShowDetailContentData.content.replace(" ", "\u00A0")
        contentContent.text = monthShowDetailContentData.content

        contentGoShow.onClick {
            itemView.context.startActivity<StageInfoActivity>("idx" to monthShowDetailContentData.showIdx)
        }
    }
}