package com.dazzi.goldenticket.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.R
import kotlinx.android.synthetic.main.activity_my_lottery_detail.*
import kotlinx.android.synthetic.main.activity_my_lottery_nothing.*
import kotlinx.android.synthetic.main.activity_my_lottery_nothing.btn_mylottery_nothing_stagelist
import kotlinx.android.synthetic.main.activity_my_lottery_nothing.iv_mylottery_nothing_character
import org.jetbrains.anko.startActivity

class MyLotteryNothingActivity : AppCompatActivity() {

    // 메인에서 티켓아이콘 onclick시, 미응모 상태화면

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_lottery_nothing)

        Glide.with(this)
            .load(R.drawable.catching)
            .into(iv_mylottery_nothing_character)

        btn_mylottery_nothing_stagelist.setOnClickListener {
            finish()
            startActivity<SearchActivity>()
        }
        ibtn_lottery_nothing_close.setOnClickListener {
            finish()
        }
    }
}