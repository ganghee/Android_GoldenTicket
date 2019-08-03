package com.dazzi.goldenticket.network.get

import com.dazzi.goldenticket.data.ShowData

data class GetMainPosterResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<ShowData>?
)
