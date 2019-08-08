package com.dazzi.goldenticket.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dazzi.goldenticket.adapter.MainPagerAdapter
import com.dazzi.goldenticket.data.MyLotteryData
import com.dazzi.goldenticket.db.SharedPreferenceController.getUserName
import com.dazzi.goldenticket.db.SharedPreferenceController.getUserToken
import com.dazzi.goldenticket.network.Controller
import com.dazzi.goldenticket.network.NetworkService
import com.dazzi.goldenticket.network.get.GetMyLotteryResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayout.GRAVITY_FILL
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_my_lottery.*
import kotlinx.android.synthetic.main.toolbar_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val backButtonSubject: io.reactivex.subjects.Subject<Long> =
        BehaviorSubject.createDefault(0L)
    lateinit var backPressedDisposable: Disposable
    var dataCount = 0
    val networkService: NetworkService by lazy {
        Controller.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.dazzi.goldenticket.R.layout.activity_main)

        configureMainTab()
        //통신을 통해 당첨 티켓 갯수를 가져옴
        getMyLotteryResponse()

        //뒤로가기 버튼을 한 번 누른 뒤 1.5초 이내에 한 번 더 누르면 종료가 된다.
        backPressedDisposable = backButtonSubject
            .buffer(2, 1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                if (t[1] - t[0] <= 1500) {
                    finishAffinity()
                    System.runFinalization()
                    System.exit(0)
                } else Toast.makeText(this, "뒤로가기 버튼을 한 번 더 누르면 종료", Toast.LENGTH_SHORT).show()
            }

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = getString(com.dazzi.goldenticket.R.string.msg_token_fmt, token)
                Log.d("TAG", msg)
            })
        Log.d("TOKENTEST: ", getUserToken(this))

        /** 상단 티켓 아이콘 **/
        iv_main_ticket?.onClick {
            startActivity<MyLotteryDetailActivity>()
        }

        val uName = getUserName(this)
        tv_main_name?.text = uName
        tv_profile_name?.text = uName

        iv_main_side?.setOnClickListener {
            dl.openDrawer(ll_drawer)
        }
        iv_drawer_close?.setOnClickListener {
            dl.closeDrawers()
        }

        /** 드로워 부분 **/
        drawerSelected()


    }

    private fun configureMainTab() {

        tabLayout.tabGravity = GRAVITY_FILL
        viewPager.adapter = MainPagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = 2
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onStart() {
        super.onStart()
        val uName = getUserName(this)
        tv_main_name?.text = uName
        tv_profile_name?.text = uName
    }


    private fun drawerSelected() {
        ll_user_update.setOnClickListener {
            startActivity<UserUpdateActivity>()
        }
        rl_win.setOnClickListener {
            startActivity<MyLotteryActivity>()
        }
        rl_like.setOnClickListener {
            startActivity<KeepActivity>()
        }
        rl_notice.setOnClickListener {
            startActivity<NoticeActivity>()
        }
        rl_settings.setOnClickListener {
            startActivity<SettingsActivity>()
        }
        rl_FAQ.setOnClickListener {
            startActivity<QuestionActivity>()
        }
    }

    override fun onBackPressed() {
        backButtonSubject.onNext(System.currentTimeMillis())
    }

    private fun getMyLotteryResponse() {

        val token = getUserToken(this)

        val getMyLotteryResponse = networkService.getMyLotteryResponse("application/json", token)
        getMyLotteryResponse.enqueue(object : Callback<GetMyLotteryResponse> {
            override fun onFailure(call: Call<GetMyLotteryResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<GetMyLotteryResponse>, response: Response<GetMyLotteryResponse>) {
                if (response.isSuccessful) {

                    dataCount = response.body()?.data!!.size
                    tv_win_num.text = dataCount.toString()


                } else {
                }
            }
        })
    }
}
