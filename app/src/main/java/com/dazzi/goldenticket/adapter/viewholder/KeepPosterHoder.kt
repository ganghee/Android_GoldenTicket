package com.dazzi.goldenticket.adapter.viewholder

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.activity.StageInfoActivity
import com.dazzi.goldenticket.data.KeepShowData
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class KeepPosterHoder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imgThumbnail = itemView.findViewById(R.id.ivKeepStage) as ImageView
    val btnLike = itemView.findViewById(R.id.ibtnKeepShowLike) as ImageButton

    fun bind(keepShowData: KeepShowData) {
        Glide.with(itemView)
            .load(keepShowData.image_url)
            .into(imgThumbnail)
        btnLike.isSelected = true

        imgThumbnail.onClick {
            itemView.context.startActivity<StageInfoActivity>("idx" to keepShowData.show_idx)
        }
    }
}