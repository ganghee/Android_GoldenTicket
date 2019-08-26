package com.dazzi.goldenticket.network.get

import com.dazzi.goldenticket.data.MonthShowListData

data class GetCardListResponse(
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<MonthShowListData>
)