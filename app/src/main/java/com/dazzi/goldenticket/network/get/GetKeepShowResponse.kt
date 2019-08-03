package com.dazzi.goldenticket.network.get

import com.dazzi.goldenticket.data.KeepShowData

data class GetKeepShowResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<KeepShowData>
)