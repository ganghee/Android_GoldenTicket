package com.dazzi.goldenticket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.adapter.viewholder.MyLotteryHolder
import com.dazzi.goldenticket.data.MyLotteryData

class MyLotteryRVAdapter : RecyclerView.Adapter<MyLotteryHolder>() {

    private val myLotteryList = mutableListOf<MyLotteryData>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int) =
        MyLotteryHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_item_mylottery, viewGroup, false))


    override fun getItemCount() = myLotteryList.size

    override fun onBindViewHolder(holder: MyLotteryHolder, position: Int) {
        holder.bind(myLotteryList[position])
    }

    fun setData(setLotteryData: List<MyLotteryData>) {
        this.myLotteryList.clear()
        this.myLotteryList.addAll(setLotteryData)
        notifyDataSetChanged()
    }

}