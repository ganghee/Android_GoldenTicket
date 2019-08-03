package com.dazzi.goldenticket.network.get

import com.dazzi.goldenticket.data.LotteryConfirmVPDetailData

data class GetLotteryConfirmDetailResponse (
     var status: Int,
     var success: Boolean,
     var message: String,
     var data: ArrayList<LotteryConfirmVPDetailData>
)