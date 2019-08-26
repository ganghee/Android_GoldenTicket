package com.dazzi.goldenticket.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.db.SharedPreferenceController
import com.dazzi.goldenticket.network.Controller
import com.dazzi.goldenticket.network.NetworkService
import com.dazzi.goldenticket.network.post.PostLotteryResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_lottery_notice.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LotteryNoticeActivity : AppCompatActivity() {

    val jsonObject = JSONObject()

    val networkService: NetworkService by lazy {
        Controller.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottery_notice)

        Glide.with(this)
            .load(R.drawable.notice)
            .into(ivNotice)


        val showIdx = intent.getIntExtra("schedule_idx", -1)
        Log.e("LottNoticeActi::", "onCreate::schedule_idx::$showIdx")

        jsonObject.put("schedule_idx", showIdx)

        btnGoLottery.setOnClickListener {
            postLotteryResponse()
        }

        ibtn_lottery_notice_close.onClick { finish() }

    }

    private fun postLotteryResponse() {
        val token = SharedPreferenceController.getUserToken(this)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postLotteryResponse: Call<PostLotteryResponse> =
            networkService.postLotteryResponse("application/json", token, gsonObject)
        postLotteryResponse.enqueue(object : Callback<PostLotteryResponse> {
            override fun onFailure(call: Call<PostLotteryResponse>, t: Throwable) {
                Log.e("LotteryCompleteActi::", "postLotteryResponse::Post_Lottery_Register_Fail")
            }

            override fun onResponse(call: Call<PostLotteryResponse>, response: Response<PostLotteryResponse>) {
                if (response.isSuccessful) {
                    when (response.body()!!.status) {
                        200 -> {
                            startActivityForResult(
                                Intent(
                                    this@LotteryNoticeActivity,
                                    LotteryCompleteActivity::class.java
                                ), 0
                            )
                            finish()
                        }
                        205 -> {
                            toast(response.body()!!.message)
                            finish()
                        }
                        204 -> {
                            toast(response.body()!!.message)
                            finish()
                        }
                    }
                } else {
                    Log.e(
                        "LotteryCompleteActi::",
                        "postLotteryResponse::onResponse::Fail::" + response.body()!!.message
                    )
                    toast(response.body()!!.message)
                }
            }
        })
    }

}