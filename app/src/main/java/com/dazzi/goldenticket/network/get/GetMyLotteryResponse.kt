package com.dazzi.goldenticket.network.get

import com.dazzi.goldenticket.data.MyLotteryData

data class GetMyLotteryResponse(
    val status: String,
    val success: Boolean,
    val message: String,
    val data: ArrayList<MyLotteryData>
)