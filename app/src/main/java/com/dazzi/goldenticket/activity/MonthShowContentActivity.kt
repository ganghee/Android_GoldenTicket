package com.dazzi.goldenticket.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.adapter.MonthShowDetailAdapter
import com.dazzi.goldenticket.data.MonthShowDetailContentData
import com.dazzi.goldenticket.network.Controller
import com.dazzi.goldenticket.network.NetworkService
import com.dazzi.goldenticket.network.get.GetMonthShowDetailResponse
import kotlinx.android.synthetic.main.activity_month_contents.*
import kotlinx.android.synthetic.main.close_custom_actionbar.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MonthShowContentActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        Controller.instance.networkService
    }
    private val monthShowDetailAdapter by lazy{ MonthShowDetailAdapter() }


    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month_contents)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        /** 카드 취소 버튼  **/
        btnClose.onClick {
            finish()
        }

        /** 카드 상세 조회 **/
        var monthShowDetailContentDataList: List<MonthShowDetailContentData>

        val getCardDetail = networkService.getCardDetail("application/json", intent.getIntExtra("idx", 2))
        getCardDetail.enqueue(object : Callback<GetMonthShowDetailResponse> {
            override fun onFailure(call: Call<GetMonthShowDetailResponse>, t: Throwable) {
                Log.e("Get CardDetail Failed: ", t.toString())
            }

            override fun onResponse(call: Call<GetMonthShowDetailResponse>, response: Response<GetMonthShowDetailResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {

                        Glide.with(applicationContext)
                            .load(response.body()!!.data.imageUrl)
                            .into(ivCardImage)

                        val tempContentArray = response.body()?.data?.title?.split("/r")
                        var tempString = ""
                        for (i:Int in 0 until tempContentArray!!.size) {
                            tempString += tempContentArray[i] + " "
                        }
                        tvCardTitle.text = tempString

                        response.body()?.data?.apply {
                            cardContent.replace(" ", "\u00A0")}
                        tvCardContent.text = response.body()?.data?.cardContent

                        monthShowDetailContentDataList = response.body()!!.data.content
                        Log.d("monthShowContent",""+response.body()!!.data)

                        monthShowDetailContentDataList.let { monthShowDetailAdapter.setData(it) }

                        with(rvContent){
                            adapter = monthShowDetailAdapter
                            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
                            setHasFixedSize(true)
                        }

                    }
                }
            }

        })

    }
}