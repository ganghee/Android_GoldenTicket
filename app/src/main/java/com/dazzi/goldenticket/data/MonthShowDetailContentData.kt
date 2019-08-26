package com.dazzi.goldenticket.data

import com.google.gson.annotations.SerializedName

data class MonthShowDetailContentData(
    @SerializedName("content")
    var content: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("show_idx")
    val showIdx: Int,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("title")
    val title: String
)