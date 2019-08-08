package com.dazzi.goldenticket.data

import com.google.gson.annotations.SerializedName

data class ShowAllData(
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("showIdx")
    val showIdx: Int
)
