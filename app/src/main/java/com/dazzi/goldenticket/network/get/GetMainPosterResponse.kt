package com.dazzi.goldenticket.network.get

import com.dazzi.goldenticket.data.ShowTodayData

data class GetMainPosterResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: List<ShowTodayData>
)
