package com.dazzi.goldenticket.network.GET

data class GetLotteryConfirmResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<String>
)