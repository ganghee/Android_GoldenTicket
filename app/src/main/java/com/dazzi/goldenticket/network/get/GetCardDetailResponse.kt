package com.dazzi.goldenticket.network.get

import com.dazzi.goldenticket.data.CardDetailData

data class GetCardDetailResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: CardDetailData
)