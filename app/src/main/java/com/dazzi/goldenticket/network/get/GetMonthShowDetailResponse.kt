package com.dazzi.goldenticket.network.get

import com.dazzi.goldenticket.data.MonthShowDetailData

data class GetCardDetailResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: MonthShowDetailData
)