package com.dazzi.goldenticket.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.activity.StageInfoActivity
import com.dazzi.goldenticket.adapter.viewholder.TodayPosterHolder
import com.dazzi.goldenticket.data.ShowTodayData
import org.jetbrains.anko.startActivity

class MainTodayPosterAdapter(val ctx: Context) :
    RecyclerView.Adapter<TodayPosterHolder>() {

    private val mainPosterList = mutableListOf<ShowTodayData>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        TodayPosterHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.cv_poster_main,
                viewGroup,
                false
            )
        )

    override fun getItemCount() = mainPosterList.size

    override fun onBindViewHolder(holder: TodayPosterHolder, position: Int) {

        Glide.with(ctx)
            .load(mainPosterList[position].image_url)
            .into(holder.poster)
        holder.showName.text = mainPosterList[position].name
        holder.location.text = mainPosterList[position].location
        holder.time.text = mainPosterList[position].running_time

        holder.container.setOnClickListener {
            ctx.startActivity<StageInfoActivity>(
                "idx" to mainPosterList[position].show_idx
            )
        }
    }

    fun setData(setTodayDataList: List<ShowTodayData>) {
        this.mainPosterList.clear()
        this.mainPosterList.addAll(setTodayDataList)
        notifyDataSetChanged()
    }
}