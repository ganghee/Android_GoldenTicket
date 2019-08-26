package com.dazzi.goldenticket.data

import com.google.gson.annotations.SerializedName

data class MonthShowDetailData (
    @SerializedName("card_content")
    val cardContent: String,
    @SerializedName("card_idx")
    val cardIdx: Int,
    @SerializedName("category")
    val category: String,
    @SerializedName("content")
    val content: List<MonthShowDetailContentData>,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("title")
    val title: String
)