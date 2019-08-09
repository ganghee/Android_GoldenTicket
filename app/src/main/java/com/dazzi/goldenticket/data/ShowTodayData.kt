package com.dazzi.goldenticket.data

data class ShowTodayData(
    val show_idx: Int,
    val image_url: String,
    val name: String,
    val location: String,
    val running_time: String
)
