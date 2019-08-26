package com.dazzi.goldenticket.data

import com.google.gson.annotations.SerializedName

data class StageInfoSchedulesData (
    @SerializedName("draw_available")
    val drawAvailable: Int,
    @SerializedName("schedule_idx")
    val scheduleIdx: Int,
    @SerializedName("time")
    val time: String
)