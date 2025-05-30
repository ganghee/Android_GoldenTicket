package com.dazzi.goldenticket.network

import com.dazzi.goldenticket.network.delete.DeleteShowLikeResponse
import com.dazzi.goldenticket.network.delete.DeleteUserResponse
import com.dazzi.goldenticket.network.get.GetLotteryListResponse
import com.dazzi.goldenticket.network.get.*
import com.dazzi.goldenticket.network.post.*
import com.dazzi.goldenticket.network.get.GetMyLotteryDetailResponse
import com.dazzi.goldenticket.network.get.GetMyLotteryResponse
import com.dazzi.goldenticket.network.get.GetStageInfoResponse
import com.dazzi.goldenticket.network.get.GetMonthShowDetailResponse
import com.dazzi.goldenticket.network.get.GetCardListResponse
import com.dazzi.goldenticket.network.post.PostSignupResponse
import com.dazzi.goldenticket.network.put.PutUserResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    //post방식은 HTTP Request의 Body에 Json 포맷으로 데이터를 담아서 전달.
    //로그인 api
    @POST("/auth/signin")
    fun postLoginResponse(
        @Header("Content-Type") content_type: String,
        @Body body: JsonObject
    ): Call<PostLoginResponse>

    //회원가입 api
    @POST("/auth/signup")
    fun postSignupResponse(
        @Header("Content-Type") content_type: String,
        @Body body: JsonObject
    ): Call<PostSignupResponse>


    //관심있는 공연 조회
    @GET("/show/heart")
    fun getKeepShow(
        @Header("Content-Type") content_type: String,
        @Header("token") user_token: String
    ): Call<GetKeepShowResponse>

    //메인 뷰의 공연 리스트 조회
    @GET("/show/home")
    fun getMainPosterResponse(
        @Header("Content-Type") content_type: String
    ): Call<GetMainPosterResponse>

    // 메인 뷰의 이번 달 리스트 조회
    @GET("card")
    fun getCardList(
        @Header("Content-Type") content_type: String
    ): Call<GetCardListResponse>


    // 카드 상세 조회 (RV 아닌 부분)
    @GET("card/{id}")
    fun getCardDetail(
        @Header("Content-Type") content_type: String,
        @Path("id") card_id: Int
    ): Call<GetMonthShowDetailResponse>


    // 좋아요
    @POST("show/like")
    fun postShowLike(
        @Header("Content-Type") content_type: String,
        @Header("token") user_token: String,
        @Body body:JsonObject
    ): Call<PostShowLikeResponse>


    // 좋아요 취소
    @HTTP(method = "DELETE", path = "show/like", hasBody = true)
    fun deleteShowLike(
        @Header("Content-Type") content_type: String,
        @Header("token") user_token: String,
        @Body body:JsonObject
    ): Call<DeleteShowLikeResponse>


    //공연 상세
    @GET("/show/detail/{id}")
    fun getStageInfoResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token: String,
        @Path("id") show_idx: Int
    ): Call<GetStageInfoResponse>

    //회원정보 수정
    @PUT("/auth/user")
    fun putUserResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token: String,
        @Body body: JsonObject
    ): Call<PutUserResponse>

    // 당첨 확인 리스트 조회
    @GET("/lottery")
    fun getLotteryListResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") user_token: String
    ): Call<GetLotteryListResponse>


    // 당첨 확인 상세 조회
    @GET("/lottery/{id}")
    fun getLotteryConfirmDetail(
        @Header("Content-Type") content_type: String,
        @Header("token") user_token: String,
        @Path("id") lottery_id: Int
    ): Call<GetLotteryConfirmDetailResponse>


    //공연내역 리스트 조회
    @GET("/ticket")
    fun getMyLotteryResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token: String
    ): Call<GetMyLotteryResponse>

    //공연내역 상세 조회, 티켓인덱스
    @GET("/ticket/{id}")
    fun getMyLotteryDetailResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token: String,
        @Path("id") ticket_idx: Int
    ): Call<GetMyLotteryDetailResponse>

    //공연내역 상세 조회, 토큰
    @GET("/ticket/detail")
    fun getMyLotteryDetailAvailableResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token: String
    ): Call<GetMyLotteryDetailResponse>

    //검색(텍스트)
    @POST("/search/text")
    fun postSearchResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token: String,
        @Body body: JsonObject
    ): Call<PostSearchResponse>

    //검색(추천검색어)
    @POST("/search")
    fun postSearchTagResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token: String,
        @Body body: JsonObject
    ): Call<PostSearchResponse>

    //응모
    @POST("/lottery")
    fun postLotteryResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token: String,
        @Body body: JsonObject
    ): Call<PostLotteryResponse>

    // 회원탈퇴
    @HTTP(method = "DELETE", path = "/auth/user", hasBody = false)
    fun deleteUserResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") user_token: String
    ): Call<DeleteUserResponse>

    //메인 ALL 포스터 가져오기
    @GET("/show/all")
    fun getAllPosterResponse(
        @Header("Content-Type") content_type: String
    ): Call<GetAllPosterResponse>
}
