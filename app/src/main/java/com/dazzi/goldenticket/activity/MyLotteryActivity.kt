package com.dazzi.goldenticket.activity


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.adapter.MyLotteryRVAdapter
import com.dazzi.goldenticket.data.MyLotteryData
import com.dazzi.goldenticket.db.SharedPreferenceController
import com.dazzi.goldenticket.network.Controller
import com.dazzi.goldenticket.network.NetworkService
import com.dazzi.goldenticket.network.get.GetMyLotteryResponse
import kotlinx.android.synthetic.main.activity_my_lottery.*
import kotlinx.android.synthetic.main.toolbar_drawer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyLotteryActivity : AppCompatActivity() {

    //메인 메뉴에서 "당첨내역" 탭 onclick시, 당첨내역 리사이클러뷰 화면

    val networkService: NetworkService by lazy {
        Controller.instance.networkService
    }

    private val myLotteryAdapter = MyLotteryRVAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_lottery)

        tb_title.text = "당첨내역"
        iv_back.setOnClickListener {
            finish()
        }
        rv_mylottery.adapter = myLotteryAdapter
        rv_mylottery.layoutManager = LinearLayoutManager(this)
        getMyLotteryResponse()
    }


    private fun getMyLotteryResponse() {

        val token = SharedPreferenceController.getUserToken(this@MyLotteryActivity)

        val getMyLotteryResponse = networkService.getMyLotteryResponse("application/json", token)
        getMyLotteryResponse.enqueue(object : Callback<GetMyLotteryResponse> {
            override fun onFailure(call: Call<GetMyLotteryResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<GetMyLotteryResponse>, response: Response<GetMyLotteryResponse>) {
                if (response.isSuccessful) {
                    val lotteryData: ArrayList<MyLotteryData> = response.body()!!.data
                    myLotteryAdapter.setData(lotteryData)

                    if (lotteryData.isEmpty()) {
                        mylottery_empty_image.visibility = View.VISIBLE
                        rv_mylottery.visibility = View.GONE
                    }

                } else {
                }
            }
        })
    }

}