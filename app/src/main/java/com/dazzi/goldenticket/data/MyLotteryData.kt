package com.dazzi.goldenticket.data

import com.google.gson.annotations.SerializedName

data class MyLotteryData(
    @SerializedName("date")
    val date: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("qr_code")
    val qrCode: String,
    @SerializedName("running_time")
    val runningTime: String,
    @SerializedName("seat_name")
    val seatName: String,
    @SerializedName("seat_type")
    val seatType: String,
    @SerializedName("ticket_idx")
    val ticketIdx: Int
)