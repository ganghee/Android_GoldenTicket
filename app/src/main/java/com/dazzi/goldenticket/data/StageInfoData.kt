package com.dazzi.goldenticket.data

import com.google.gson.annotations.SerializedName

data class StageInfoData (
    @SerializedName("background_image")
    val backgroundImage: String,
    @SerializedName("discount_price")
    val discountPrice: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("is_liked")
    val isLiked: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("lottery_available")
    val lotteryAvailable: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("original_price")
    val originalPrice: String,
    @SerializedName("schedule")
    val schedule: List<StageInfoSchedulesData>,
    @SerializedName("show_idx")
    val showIdx: Int,
    @SerializedName("artist")
    val artist: List<StageInfoActorsData>,
    @SerializedName("poster")
    val poster: List<StageInfoImgsData>
)