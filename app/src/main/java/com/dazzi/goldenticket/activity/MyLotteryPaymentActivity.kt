package com.dazzi.goldenticket.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.data.MyLotteryDetailData
import com.dazzi.goldenticket.network.Controller
import com.dazzi.goldenticket.network.NetworkService
import com.dazzi.goldenticket.network.get.GetMyLotteryDetailResponse
import kotlinx.android.synthetic.main.activity_my_lottery_payment.*
import kotlinx.android.synthetic.main.toolbar_drawer.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyLotteryPaymentActivity : AppCompatActivity() {

    // 메인화면에서 티켓아이콘 onclick시, 결제 안내 화면
    val networkService: NetworkService by lazy {
        Controller.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_lottery_payment)

        getMyLotteryResponse()

        tb_title.text = "당첨 티켓"
        iv_back.setOnClickListener {
            startActivity<MainActivity>()
        }

        btn_mylottery_payment_submit.setOnClickListener {
            startActivity<MainActivity>()
        }
    }

    private fun getMyLotteryResponse() {
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4Ijo0LCJlbWFpbCI6ImVtYWlsMzMyNEBuYXZlci5jb20iLCJpYXQiOjE1NjIzMjE4ODZ9.JUsSqUu8OWnBAb3Hjt8uB09vHQV-eZ3VEiq8q8CHTk0"

        val getMyLotteryDetailResponse = networkService.getMyLotteryDetailResponse(
            "application/json",
            token,
            intent.getIntExtra("idx", -1)
        ) //메인(->LotteryConfirm,status=1)-> idx로 보내준 ticket_idx
        getMyLotteryDetailResponse.enqueue(object : Callback<GetMyLotteryDetailResponse> {
            override fun onFailure(call: Call<GetMyLotteryDetailResponse>, t: Throwable) {
                Log.e("MyLottPaymentActivity::", "GET_My_Lottery_Data_Failed")
            }

            override fun onResponse(
                call: Call<GetMyLotteryDetailResponse>,
                response: Response<GetMyLotteryDetailResponse>
            ) {
                if (response.isSuccessful) {

                    val tempData: MyLotteryDetailData = response.body()!!.data
                    Glide.with(this@MyLotteryPaymentActivity)
                        .load(response.body()!!.data.image_url)
                        .into(iv_mylottery_payment_poster)

                    tv_mylottery_payment_title.text = response.body()!!.data.name
                    tv_mylottery_payment_price.text = response.body()!!.data.price
                } else {
                    Log.e("MyLottDetailActivity::", "onResponse::Fail")
                }
            }
        })
    }
}