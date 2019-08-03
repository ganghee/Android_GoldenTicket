package com.dazzi.goldenticket.network.get

import com.dazzi.goldenticket.data.CardListData

data class GetCardListResponse(
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<CardListData>
)