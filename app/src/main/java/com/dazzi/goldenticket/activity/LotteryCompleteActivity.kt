package com.dazzi.goldenticket.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.db.SharedPreferenceController
import com.dazzi.goldenticket.network.Controller
import com.dazzi.goldenticket.network.NetworkService
import kotlinx.android.synthetic.main.activity_lottery_complete.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.json.JSONObject

class LotteryCompleteActivity : AppCompatActivity() {

    val jsonObject = JSONObject()

    val networkService: NetworkService by lazy {
        Controller.instance.networkService
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottery_complete)

        Log.e("LottCompleteActi::", "onCreate::schedule_idx::" + intent.getIntExtra("idx", -1))

        Glide.with(this)
            .load(R.drawable.complete)
            .into(ivComplete)

        tvCommentLuck.text = SharedPreferenceController.getUserName(this@LotteryCompleteActivity) + "님 행운을 빌어요!"
        btnOkay.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
        ibtn_lottery_complete_close.onClick { finish() }
    }

}