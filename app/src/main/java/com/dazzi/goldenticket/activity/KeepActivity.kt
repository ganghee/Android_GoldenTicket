package com.dazzi.goldenticket.activity


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.adapter.KeepStageRVAdapter
import com.dazzi.goldenticket.data.KeepShowData
import com.dazzi.goldenticket.db.SharedPreferenceController
import com.dazzi.goldenticket.etc.RecyclerViewDecoration
import com.dazzi.goldenticket.network.Controller
import com.dazzi.goldenticket.network.NetworkService
import com.dazzi.goldenticket.network.get.GetKeepShowResponse
import kotlinx.android.synthetic.main.activity_keep.*
import kotlinx.android.synthetic.main.toolbar_drawer.*
import kotlinx.android.synthetic.main.toolbar_drawer.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class KeepActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        Controller.instance.networkService
    }
    lateinit var keepShowDataList: ArrayList<KeepShowData>

    private val keepStageRVAdapter = KeepStageRVAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keep)

        swipeKeepRefresh.onRefresh {
            getKeepShowResponse()
        }
        swipeKeepRefresh.setColorSchemeResources(
            R.color.colorPrimaryDarkYellow,
            R.color.colorPrimaryYellow
        )
        /** 툴바 타이틀 설정 **/
        tb_title.text = "관심있는 공연"
        tb_keep.iv_back.onClick {
            finish()
        }

        rvKeepStage.apply {
            adapter = keepStageRVAdapter
            layoutManager = GridLayoutManager(applicationContext, 2)
            addItemDecoration(RecyclerViewDecoration())
            setHasFixedSize(true)
        }
        getKeepShowResponse()

    }

    override fun onResume() {
        super.onResume()
        getKeepShowResponse()
    }

    private fun getKeepShowResponse() {
        val getKeepShowData =
            networkService.getKeepShow("application/json", SharedPreferenceController.getUserToken(this))

        getKeepShowData.enqueue(object : Callback<GetKeepShowResponse> {
            override fun onFailure(call: Call<GetKeepShowResponse>, t: Throwable) {
                Log.e("Get CardDetail Failed: ", t.toString())
                swipeKeepRefresh.isRefreshing = false
            }

            override fun onResponse(call: Call<GetKeepShowResponse>, response: Response<GetKeepShowResponse>) {
                if (response.isSuccessful) {
                    swipeKeepRefresh.isRefreshing = false
                    if (response.body()!!.status == 200) {

                        keepShowDataList = response.body()!!.data
                        keepStageRVAdapter.setData(keepShowDataList)

                        if (keepShowDataList.isEmpty()) {
                            keep_empty_image.visibility = View.VISIBLE
                            swipeKeepRefresh.visibility = View.GONE
                        } else {
                            keep_empty_image.visibility = View.GONE
                            swipeKeepRefresh.visibility = View.VISIBLE
                        }
                    }
                }
            }

        })

    }
}