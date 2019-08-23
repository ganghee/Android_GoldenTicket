package com.dazzi.goldenticket.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.adapter.StageInfoActorsRVAdapter
import com.dazzi.goldenticket.adapter.StageInfoImgsRVAdpater
import com.dazzi.goldenticket.data.StageInfoActorsData
import com.dazzi.goldenticket.data.StageInfoData
import com.dazzi.goldenticket.data.StageInfoImgsData
import com.dazzi.goldenticket.data.StageInfoSchedulesData
import com.dazzi.goldenticket.db.SharedPreferenceController
import com.dazzi.goldenticket.network.Controller
import com.dazzi.goldenticket.network.NetworkService
import com.dazzi.goldenticket.network.delete.DeleteShowLikeResponse
import com.dazzi.goldenticket.network.get.GetStageInfoResponse
import com.dazzi.goldenticket.network.post.PostShowLikeResponse
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_stage_info.*
import kotlinx.android.synthetic.main.fragment_stage_info_entry_dialog.*
import kotlinx.android.synthetic.main.toolbar_stage_info.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StageInfoActivity : BaseActivity() {

    val networkService: NetworkService by lazy {
        Controller.instance.networkService
    }
    var actors: List<StageInfoActorsData> = ArrayList()
    var stageImgs : List<StageInfoImgsData> = ArrayList()
    var schedules: List<StageInfoSchedulesData> = ArrayList()
    var times: MutableMap<String, Int> = mutableMapOf()

    var showIdx: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Action Bar Custom
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setContentView(R.layout.activity_stage_info)

        showIdx = intent.getIntExtra("idx", -1)

        setRecyclerView()
        getStageInfoResponse()
        setOnClickListener()
    }


    private fun setOnClickListener() {
        ibtn_stageinfo_pre.setOnClickListener {
            finish()
        }
        ibtn_stageinfo_like.setOnClickListener {
            when (ibtn_stageinfo_like.isSelected) {
                true -> {
                    //취소통신
                    val jsonObject = JSONObject()
                    jsonObject.put("show_idx", showIdx)
                    val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
                    val token = SharedPreferenceController.getUserToken(this)
                    // TODO: user token 쉐어드에서 가져와야함
                    val deleteShowLike = networkService.deleteShowLike("application/json", token, gsonObject)
                    deleteShowLike.enqueue(object : Callback<DeleteShowLikeResponse> {
                        override fun onFailure(call: Call<DeleteShowLikeResponse>, t: Throwable) {
                            Log.e("Delete ShowLike Failed:", t.toString())
                        }

                        override fun onResponse(
                            call: Call<DeleteShowLikeResponse>,
                            response: Response<DeleteShowLikeResponse>
                        ) {
                            if (response.isSuccessful) {
                                if (response.body()!!.status == 200) {
                                    ibtn_stageinfo_like.isSelected = false
                                }
                            }
                        }

                    })
                }
                false -> {
                    val jsonObject = JSONObject()
                    jsonObject.put("show_idx", showIdx)
                    val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
                    val token = SharedPreferenceController.getUserToken(this)
                    // TODO: user token 쉐어드에서 가져와야함
                    val postShowLike = networkService.postShowLike("application/json", token, gsonObject)
                    postShowLike.enqueue(object : Callback<PostShowLikeResponse> {
                        override fun onFailure(call: Call<PostShowLikeResponse>, t: Throwable) {
                            Log.e("Post ShowLike Failed:", t.toString())
                        }

                        override fun onResponse(
                            call: Call<PostShowLikeResponse>,
                            response: Response<PostShowLikeResponse>
                        ) {
                            if (response.isSuccessful) {
                                if (response.body()!!.status == 200) {
                                    ibtn_stageinfo_like.isSelected = true

                                }
                            }
                        }
                    })
                }
            }
        }

        //위로 화살표를 눌렀을 때
        btn_stageinfo_activate_dl.setOnClickListener {

            val behavior = BottomSheetBehavior.from(rl_stageinfo_bottom_sheet)
            behavior.peekHeight = 1000 //정확한 height단위 전혀모르겠음, dp와 float라

            btn_stageinfo_activate_dl.visibility = View.GONE
            btn_stageinfo_inactivate_dl.visibility = View.VISIBLE
            rl_stageinfo_select_time_spinner.visibility = View.VISIBLE
            btn_stageinfo_entry.visibility = View.GONE
            btn_stageinfo_submit.visibility = View.VISIBLE
        }
        btn_stageinfo_inactivate_dl.setOnClickListener {
            btn_stageinfo_activate_dl.visibility = View.VISIBLE
            btn_stageinfo_inactivate_dl.visibility = View.GONE
            rl_stageinfo_select_time_spinner.visibility = View.GONE
            btn_stageinfo_entry.visibility = View.VISIBLE
            btn_stageinfo_submit.visibility = View.GONE
        }
        btn_stageinfo_entry.setOnClickListener {
            btn_stageinfo_activate_dl.visibility = View.GONE
            btn_stageinfo_inactivate_dl.visibility = View.VISIBLE
            rl_stageinfo_select_time_spinner.visibility = View.VISIBLE
            btn_stageinfo_entry.visibility = View.GONE
            btn_stageinfo_submit.visibility = View.VISIBLE

        }
        btn_stageinfo_submit.setOnClickListener {
            btn_stageinfo_activate_dl.visibility = View.VISIBLE
            btn_stageinfo_inactivate_dl.visibility = View.GONE
            rl_stageinfo_select_time_spinner.visibility = View.GONE
            btn_stageinfo_entry.visibility = View.VISIBLE
            btn_stageinfo_submit.visibility = View.GONE


            val intent = Intent(this, LotteryNoticeActivity::class.java)
            intent.putExtra("schedule_idx", times.get(spn_stageinfo_select_time.selectedItem.toString()))
            startActivity(intent)
            finish()

        }
    }

    private fun setRecyclerView() {
        rv_stageinfo_actors.layoutManager =
            LinearLayoutManager(this@StageInfoActivity, LinearLayoutManager.HORIZONTAL, false)
        rv_stageinfo_imgs.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        Log.d("StageInfoActivity::", "getStageInfoResponse::setRecyclerView:: " + stageImgs.toString())
        Log.d("StageInfoActivity::", "getStageInfoResponse::setRecyclerView:: " + actors.toString())
    }

    private fun setSpinner(times: MutableMap<String, Int>) {
        spn_stageinfo_select_time.adapter = ArrayAdapter(
            this,
            R.layout.spn_item_stageinfo_starttimes,
            times.keys.toTypedArray()
        )
        spn_stageinfo_select_time.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) { /*nothing*/
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) { /*nothing*/
            }
        }
    }

    private fun setBottomSheet(status: Int) {
        when (status) {
            200 -> {
                val behavior = BottomSheetBehavior.from(rl_stageinfo_bottom_sheet)
                ll_stageinfo_bottom_sheet_activate.visibility = View.VISIBLE
                rl_stageinfo_bottom_sheet_inactive.visibility = View.INVISIBLE
                behavior.setPeekHeight(800)
            }
            204 -> {
                Log.d("StageInfoActi", "setBottomSheet::status" + status)
                val behavior = BottomSheetBehavior.from(rl_stageinfo_bottom_sheet)
                ll_stageinfo_bottom_sheet_activate.visibility = View.INVISIBLE
                rl_stageinfo_bottom_sheet_inactive.visibility = View.VISIBLE
                tv_stageinfo_bottom_sheet_inactive.text = "이미 응모한 공연입니다."
                behavior.setPeekHeight(800)
            }
            205 -> {
                val behavior = BottomSheetBehavior.from(rl_stageinfo_bottom_sheet)
                ll_stageinfo_bottom_sheet_activate.visibility = View.INVISIBLE
                rl_stageinfo_bottom_sheet_inactive.visibility = View.VISIBLE
                tv_stageinfo_bottom_sheet_inactive.text = "응모는 하루에 두 번까지 가능합니다."
                behavior.setPeekHeight(800)
            }
            206 -> {
                val behavior = BottomSheetBehavior.from(rl_stageinfo_bottom_sheet)
                ll_stageinfo_bottom_sheet_activate.visibility = View.INVISIBLE
                rl_stageinfo_bottom_sheet_inactive.visibility = View.VISIBLE
                tv_stageinfo_bottom_sheet_inactive.text = "응모 가능한 시간이 아닙니다."
                behavior.setPeekHeight(800)
            }
        }
    }

    private fun getStageInfoResponse() {
        showProgressDialog()
        val token = SharedPreferenceController.getUserToken(this@StageInfoActivity)
        val getStageInfoResponse = networkService.getStageInfoResponse("application/json", token, showIdx)
        getStageInfoResponse.enqueue(object : Callback<GetStageInfoResponse> {


            override fun onFailure(call: Call<GetStageInfoResponse>, t: Throwable) {
                Log.d("StageInfoActivity:: ", "getStageInfoResponse:: Get_StageInfo_Failed")
                Log.d("StageInfoActivity:: ", t.toString())
            }

            override fun onResponse(call: Call<GetStageInfoResponse>, response: Response<GetStageInfoResponse>) {


                if (response.isSuccessful) {

                    Log.d("@#@#@###",""+response.body()!!.data.imageUrl)
                    Glide.with(this@StageInfoActivity)
                        .load(response.body()!!.data.imageUrl)
                        .into(iv_stageinfo_bg)
                    Glide.with(this@StageInfoActivity)
                        .load(response.body()!!.data.imageUrl)
                        .into(iv_stageinfo_poster)
                    tv_stageinfo_title.text = response.body()!!.data.name
                    tv_stageinfo_costprice.text = response.body()!!.data.originalPrice + "원"
                    tv_stageinfo_saleprice.text = response.body()!!.data.discountPrice + "원"
                    tv_stageinfo_date.text = response.body()!!.data.duration
                    tv_stageinfo_location.text = response.body()!!.data.location

                    ibtn_stageinfo_like.isSelected = response.body()!!.data.isLiked == 1

                    actors = response.body()!!.data.artist
                    Log.d("StageInfoActivity::", "getStageInfoResponse::actors:: " + actors.toString())
                    stageImgs = response.body()!!.data.poster
                    Log.d("!@!@!@::", "getStageInfoResponse::imgs:: " + stageImgs)

                    rv_stageinfo_actors.adapter = StageInfoActorsRVAdapter(this@StageInfoActivity, actors)
                    rv_stageinfo_imgs.adapter = StageInfoImgsRVAdpater(this@StageInfoActivity, stageImgs)

                    schedules = response.body()!!.data.schedule
                    if (schedules.size == 0) {
                        val behavior = BottomSheetBehavior.from(rl_stageinfo_bottom_sheet)
                        behavior.peekHeight = 0

                        val layoutParams = ll_stageinfo.layoutParams as FrameLayout.LayoutParams
                        layoutParams.setMargins(0, 0, 0, 0)

                    } else { // lottery available
                        for (schedule in schedules) { // 공연 스케줄을 모두 가져옴
                            if (schedule.drawAvailable == 1) { // 응모가능한(공연시작보다 3시간 이하 남은) 공연 시간을 가져옴
                                times[schedule.time] = schedule.scheduleIdx
                            }
                        }
                        if (times.isEmpty()) {
                            setBottomSheet(206)
                        } else {
                            Log.d("StageInfoActi::", "getStageInfoRes::onResponse::status::" + response.body()!!.status)
                            setBottomSheet(response.body()!!.status) // STATUS로 유저가 중복 응모를 했는지(204), 응모가능 횟수를 넘었는지(205) 확인함
                            setSpinner(times)
                        }
                    }
                    hideProgressDialog()
                } else {
                    Log.e("StageInfoActivity:: ", "getStageInfoResponse:: Fail")
                }

            }
        })
    }
}