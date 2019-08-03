package com.dazzi.goldenticket.network.get

import com.dazzi.goldenticket.data.LotteryListData

data class GetLotteryListResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<LotteryListData>
)