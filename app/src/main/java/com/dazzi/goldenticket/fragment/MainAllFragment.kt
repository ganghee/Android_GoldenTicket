package com.dazzi.goldenticket.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.adapter.MainAllPosterAdapter
import com.dazzi.goldenticket.data.ShowAllData
import com.dazzi.goldenticket.network.Controller
import com.dazzi.goldenticket.network.NetworkService
import com.dazzi.goldenticket.network.get.GetAllPosterResponse
import kotlinx.android.synthetic.main.fragment_main_all.*
import org.jetbrains.anko.support.v4.onRefresh
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainAllFragment : Fragment() {

    lateinit var mainAllPosterAdapter: MainAllPosterAdapter

    val networkService: NetworkService by lazy {
        Controller.instance.networkService
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshAll.onRefresh {
            getAllPoster()
        }
        swipeRefreshAll.setColorSchemeResources(
            R.color.colorPrimaryDarkYellow,
            R.color.colorPrimaryYellow
        )
        mainAllPosterAdapter = MainAllPosterAdapter()

        rvAllPoster.apply {
            adapter = mainAllPosterAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        getAllPoster()
    }

    private fun getAllPoster() {
        val getAllPosterResponse = networkService.getAllPosterResponse(
            "application/json"
        )
        getAllPosterResponse.enqueue(object : Callback<GetAllPosterResponse> {
            override fun onFailure(call: Call<GetAllPosterResponse>, t: Throwable) {
                Log.e("Get Card List Failed: ", t.toString())
                swipeRefreshAll.isRefreshing = false
            }

            override fun onResponse(call: Call<GetAllPosterResponse>, response: Response<GetAllPosterResponse>) {

                if (response.isSuccessful) {
                    swipeRefreshAll.isRefreshing = false
                    if (response.body()!!.status == 200) {
                        val mainAllPoster: List<ShowAllData> = response.body()!!.data

                        mainAllPosterAdapter.setData(mainAllPoster)

                        Log.d("mainAllPoster@@@", "" + mainAllPoster)

                    }
                }
            }
        })


    }


}
