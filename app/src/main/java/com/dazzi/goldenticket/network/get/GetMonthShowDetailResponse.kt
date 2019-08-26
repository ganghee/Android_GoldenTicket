package com.dazzi.goldenticket.network.get

import com.dazzi.goldenticket.data.MonthShowDetailData
import com.google.gson.annotations.SerializedName

data class GetMonthShowDetailResponse (
    @SerializedName("data")
    val `data`: MonthShowDetailData,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
)