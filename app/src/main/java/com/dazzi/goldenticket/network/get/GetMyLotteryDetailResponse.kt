package com.dazzi.goldenticket.network.get

import com.dazzi.goldenticket.data.MyLotteryDetailData

data class GetMyLotteryDetailResponse (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: MyLotteryDetailData
)