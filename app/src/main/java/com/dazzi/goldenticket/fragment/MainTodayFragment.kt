package com.dazzi.goldenticket.fragment


import android.animation.TimeInterpolator
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.activity.SearchActivity
import com.dazzi.goldenticket.adapter.LotteryConfirmAdapter
import com.dazzi.goldenticket.adapter.MainTodayPosterAdapter
import com.dazzi.goldenticket.adapter.MonthShowListAdapter
import com.dazzi.goldenticket.data.MonthShowListData
import com.dazzi.goldenticket.data.ShowTodayData
import com.dazzi.goldenticket.db.SharedPreferenceController
import com.dazzi.goldenticket.network.Controller
import com.dazzi.goldenticket.network.NetworkService
import com.dazzi.goldenticket.network.get.GetCardListResponse
import com.dazzi.goldenticket.network.get.GetLotteryListResponse
import com.dazzi.goldenticket.network.get.GetMainPosterResponse
import com.dazzi.goldenticket.network.get.GetMyLotteryResponse
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.fragment_main_today.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainTodayFragment : Fragment() {

    val networkService: NetworkService by lazy {
        Controller.instance.networkService
    }
    var tempNumFragment = 0
    lateinit var mainTodayPosterAdapter: MainTodayPosterAdapter
    lateinit var lotteryConfirmAdapter: LotteryConfirmAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_today, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshToday.onRefresh {
            getMainPosterResponse()
            getTimerResponse()
            getMyLotteryCountResponse()
            getThisMonthResponse()
        }

        mainTodayPosterAdapter = MainTodayPosterAdapter(ctx)
        swipeRefreshToday.setColorSchemeResources(
            R.color.colorPrimaryDarkYellow,
            R.color.colorPrimaryYellow
        )

        /** 상단 공연 포스터 리사이클러뷰 부분 애니메이션 **/
        configureShowRV()

        getMainPosterResponse()
        getTimerResponse()
        getMyLotteryCountResponse()
        getThisMonthResponse()

        //첫 번째 타이머와 두 번째 타이머가 되었을 때 화살표 가시성 설정
        vpLotteryConfirm?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                btnVisibilityCheck(vpLotteryConfirm.currentItem)
            }

            override fun onPageSelected(position: Int) {
            }

        })

        /** 하단 검색 창 클릭 부분 **/
        tvSearch?.onClick {
            startActivity<SearchActivity>()
        }

        ibtnNextLeft?.onClick {
            val position = vpLotteryConfirm.currentItem
            vpLotteryConfirm.setCurrentItem(position - 1, true)
            btnVisibilityCheck(vpLotteryConfirm.currentItem)
        }
        ibtnNextRight?.onClick {
            val position = vpLotteryConfirm.currentItem
            vpLotteryConfirm.setCurrentItem(position + 1, true)
            btnVisibilityCheck(vpLotteryConfirm.currentItem)
        }
    }

    private fun configureShowRV() {
        val linearLayoutManager = LinearLayoutManager(
            ctx,
            LinearLayoutManager.HORIZONTAL, false
        )

        rv_product.apply {
            adapter = mainTodayPosterAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(MarginItemDecoration(40, 0))
        }

        val snapHelper = GravitySnapHelper(Gravity.START)
        snapHelper.attachToRecyclerView(rv_product)

        //스크롤이 되었을 때 아이템의 크기가 변화된다.
        rv_product?.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    //스크롤을 하지 않은 상태
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val view: View? = snapHelper.findSnapView(linearLayoutManager)
                        val pos = linearLayoutManager.getPosition(view!!)

                        val viewHolder = rv_product.findViewHolderForAdapterPosition(pos)

                        val eventparent =
                            viewHolder!!.itemView.findViewById(R.id.cv_main_poster) as CardView
                        eventparent.animate().scaleY(0.95f).scaleX(0.95f).setDuration(350)
                            .setInterpolator(AccelerateInterpolator() as TimeInterpolator?)
                            .start()

                        //스크롤 중인 상태
                    } else {

                        val view = snapHelper.findSnapView(linearLayoutManager)
                        val pos = linearLayoutManager.getPosition(view!!)

                        val viewHolder = rv_product.findViewHolderForAdapterPosition(pos)

                        val eventparent =
                            viewHolder!!.itemView.findViewById(R.id.cv_main_poster) as CardView
                        eventparent.animate().scaleY(0.85f).scaleX(0.85f).setDuration(350)
                            .setInterpolator(AccelerateInterpolator()).start()

                    }
                }
            })
    }

    //타이머 정보 가져오기
    private fun getTimerResponse() {

        val getMainLotteryListResponse = networkService.getLotteryListResponse(
            "application/json", SharedPreferenceController.getUserToken(ctx)
        )
        getMainLotteryListResponse.enqueue(object : Callback<GetLotteryListResponse> {
            override fun onFailure(call: Call<GetLotteryListResponse>, t: Throwable) {
                Log.e("Lottery List Fail", t.toString())
                tempNumFragment = 0
            }

            override fun onResponse(call: Call<GetLotteryListResponse>, response: Response<GetLotteryListResponse>) {
                Log.e("Lottery List Fail", response.body()!!.message)
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        tempNumFragment = response.body()!!.data.size
                        //tempNumFragment = 1
                        if (tempNumFragment == 0) {
                            vpLotteryConfirm.visibility = View.INVISIBLE
                            tvLotteryNothing.visibility = View.VISIBLE
                        }
                        btnVisibilityCheck(vpLotteryConfirm.currentItem)
                        lotteryConfirmAdapter = LotteryConfirmAdapter(childFragmentManager, tempNumFragment)
                        vpLotteryConfirm.adapter = lotteryConfirmAdapter
                    }
                }
            }
        })
    }

    private fun btnVisibilityCheck(position: Int) { // TODO: 당첨확인 버튼 유무 체크
        if (position == 0) {
            if (tempNumFragment == 1 || tempNumFragment == 0) {
                //position이 0이고 데이터가 하나일때, 데이터가 하나도 없을 때 화살표 버튼이 둘다 뜨면 안됨
                ibtnNextRight.visibility = View.INVISIBLE
                ibtnNextLeft.visibility = View.INVISIBLE
            } else {
                ibtnNextRight.visibility = View.VISIBLE
                ibtnNextLeft.visibility = View.INVISIBLE
            }
        } else if (position == 1) {
            ibtnNextRight.visibility = View.INVISIBLE
            ibtnNextLeft.visibility = View.VISIBLE
        }
    }

    /*리사이클러뷰 간격*/
    class MarginItemDecoration(
        private val first_space: Int,
        private val space: Int
    ) :
        RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            with(outRect) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    left = first_space
                }
                right = space
            }
        }
    }

    private fun getThisMonthResponse() {
        //progressON("Loading...")
        lateinit var monthShowListAdapter: MonthShowListAdapter

        val getCardList = networkService.getCardList("application/json")
        getCardList.enqueue(object : Callback<GetCardListResponse> {
            override fun onFailure(call: Call<GetCardListResponse>, t: Throwable) {
                Log.e("Get Card List Failed: ", t.toString())
                swipeRefreshToday.isRefreshing = false
            }

            override fun onResponse(call: Call<GetCardListResponse>, response: Response<GetCardListResponse>) {
                swipeRefreshToday.isRefreshing = false
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        val monthShowListDataList: ArrayList<MonthShowListData> = response.body()!!.data

                        monthShowListAdapter = MonthShowListAdapter()
                        rvContents.adapter = monthShowListAdapter
                        monthShowListAdapter.setData(monthShowListDataList)

                        rvContents.layoutManager =
                            LinearLayoutManager(ctx, RecyclerView.VERTICAL, false)
                        rvContents.setHasFixedSize(true)

                        Log.d("TEST", monthShowListDataList.toString())
                        Log.d("TEST", monthShowListDataList[0].category)

                    }
                }
            }
        })
    }

    private fun getMainPosterResponse() {

        val getMainPosterResponse = networkService.getMainPosterResponse(
            "application/json"
        )
        getMainPosterResponse.enqueue(object : Callback<GetMainPosterResponse> {
            override fun onFailure(call: Call<GetMainPosterResponse>, t: Throwable) {
                Log.e("Main Poster List Fail", t.toString())

            }

            override fun onResponse(call: Call<GetMainPosterResponse>, response: Response<GetMainPosterResponse>) {

                if (response.isSuccessful) {
                    if (response.body()?.status == 200) {
                        val tmp: List<ShowTodayData>? = response.body()?.data
                        mainTodayPosterAdapter.setData(tmp!!)

                        Log.d("ShowTodayData", "" + tmp)

                        when (tmp) {
                            emptyList<ShowTodayData>() -> {
                                rv_product?.visibility = View.GONE
                                img_main_no_poster.visibility = View.VISIBLE
                            }
                            else -> {
                                rv_product?.visibility = View.VISIBLE
                                img_main_no_poster.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        })
    }

    private fun getMyLotteryCountResponse() {

        val getMyLotteryResponse = networkService.getMyLotteryResponse(
            "application/json",
            SharedPreferenceController.getUserToken(ctx)
        )
        getMyLotteryResponse.enqueue(object : Callback<GetMyLotteryResponse> {
            override fun onFailure(call: Call<GetMyLotteryResponse>, t: Throwable) {
                Log.e("My Lottery Fail", t.toString())
            }

            override fun onResponse(
                call: Call<GetMyLotteryResponse>,
                response: Response<GetMyLotteryResponse>
            ) {
                if (response.isSuccessful) {
                    Log.e("MainActivity::", "getMyLotteryCountResponse::onResponse::" + response.body()!!.message)
                    val size = response.body()!!.data.size
                    tv_win_num?.text = size.toString()
                }
            }
        })
    }
}
