package com.dazzi.goldenticket.data

import com.google.gson.annotations.SerializedName

data class StageInfoImgsData (
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("poster_idx")
    val posterIdx: Int
)