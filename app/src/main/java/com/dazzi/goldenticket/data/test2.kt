package com.dazzi.goldenticket.data


import com.google.gson.annotations.SerializedName

data class test2(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean
) {
    data class Data(
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
        val schedule: List<Schedule>,
        @SerializedName("show_idx")
        val showIdx: Int
    ) {
        data class Schedule(
            @SerializedName("draw_available")
            val drawAvailable: Int,
            @SerializedName("schedule_idx")
            val scheduleIdx: Int,
            @SerializedName("time")
            val time: String
        )
    }
}