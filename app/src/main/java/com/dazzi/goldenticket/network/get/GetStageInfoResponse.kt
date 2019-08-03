package com.dazzi.goldenticket.network.get

import com.dazzi.goldenticket.data.StageInfoData

data class GetStageInfoResponse (
    val status: Int,
    val success: String,
    val message: String,
    val data: StageInfoData
)