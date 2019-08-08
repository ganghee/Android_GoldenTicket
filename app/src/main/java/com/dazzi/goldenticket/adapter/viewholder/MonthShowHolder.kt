package com.dazzi.goldenticket.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.activity.MonthShowContentActivity
import com.dazzi.goldenticket.data.MonthShowListData
import kotlinx.android.synthetic.main.rv_main_month_list.view.*
import org.jetbrains.anko.startActivity

class MonthShowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val cvMonth = itemView.cvMonth
    private val title = itemView.findViewById(R.id.tvTitle) as TextView
    private val category = itemView.findViewById(R.id.tvCategory) as TextView
    private val img = itemView.findViewById(R.id.ivCard) as ImageView

    fun bind(monthShowListData: MonthShowListData) {
        Glide.with(itemView)
            .load(monthShowListData.imageUrl)
            .into(img)

        title.text = monthShowListData.title.replace("/r", "\n")
        category.text = monthShowListData.category

        cvMonth.setOnClickListener {
            itemView.context.startActivity<MonthShowContentActivity>(
                "idx" to monthShowListData.cardIdx
            )
        }
    }

}