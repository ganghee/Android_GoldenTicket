package com.dazzi.goldenticket.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.adapter.SearchResultRVAdapter
import com.dazzi.goldenticket.db.SharedPreferenceController
import com.dazzi.goldenticket.etc.RecyclerViewDecoration
import com.dazzi.goldenticket.network.Controller
import com.dazzi.goldenticket.network.NetworkService
import com.dazzi.goldenticket.network.post.PostSearchResponse
import com.dazzi.goldenticket.network.post.SearchData
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_search_result.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultActivity : AppCompatActivity() {

    val jsonObject = JSONObject()

    val networkService: NetworkService by lazy {
        Controller.instance.networkService
    }

    var results: ArrayList<SearchData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        setOnClickListener()

        if (intent.getStringExtra("search_text") != null) {
            et_searchresult_searchbar.setText(intent.getStringExtra("search_text"))
            jsonObject.put("text", intent.getStringExtra("search_text"))
            postSearchResponse()
        }
        if (intent.getStringExtra("search_tag") != null) {
            et_searchresult_searchbar.setText(intent.getStringExtra("search_tag"))
            jsonObject.put("keyword", intent.getStringExtra("search_tag"))
            postSearchTagResponse()
        }
    }

    private fun setOnClickListener() {
        ibtn_searchresult_pre.setOnClickListener {
            finish()
        }
        ibtn_searchresult_submit.setOnClickListener {
            jsonObject.put("text", et_searchresult_searchbar.text)
            postSearchResponse()
        }
    }

    private fun setRecyclerView() {
        rv_search_results.adapter = SearchResultRVAdapter(applicationContext, results)
        rv_search_results.layoutManager = GridLayoutManager(applicationContext, 2)
        rv_search_results.addItemDecoration(RecyclerViewDecoration())
        rv_search_results.setHasFixedSize(true)
    }

    private fun postSearchResponse() {
        val token = SharedPreferenceController.getUserToken(this@SearchResultActivity)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postSearchResponse: Call<PostSearchResponse>
                = networkService.postSearchResponse("application/json", token, gsonObject)
        postSearchResponse.enqueue(object: Callback<PostSearchResponse> {
            override fun onFailure(call: Call<PostSearchResponse>, t: Throwable) {
                Log.e("SearchResultActivity:: ", "postSearchResponse::List_Search_Result_Data_Fail")
            }

            override fun onResponse(call: Call<PostSearchResponse>, response: Response<PostSearchResponse>) {
                if (response.isSuccessful) {
                    var tempData: ArrayList<SearchData> = response.body()!!.data
                    Log.e("SearchResultActivity::", "postSearchResponse::onResponse::Success:: " + response.body()!!.message)
                    results = response.body()!!.data
                    setRecyclerView()
                }
                else {
                    Log.e("SearchResultActivity::", "postSearchResponse::onResponse::Fail:: " + response.body()!!.message)
                }
            }
        })
    }

    private fun postSearchTagResponse() {
        val token = SharedPreferenceController.getUserToken(this@SearchResultActivity)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postSearchTagResponse: Call<PostSearchResponse> = networkService.postSearchTagResponse("application/json", token, gsonObject)
        postSearchTagResponse.enqueue(object: Callback<PostSearchResponse> {
            override fun onFailure(call: Call<PostSearchResponse>, t: Throwable) {
                Log.e("SearchResultActivity:: ", "postSearchTagResponse::List_Search_Tag_Result_Data_Fail")
            }

            override fun onResponse(call: Call<PostSearchResponse>, response: Response<PostSearchResponse>) {
                if (response.isSuccessful) {
                    Log.e("SearchResultActivity::", "postSearchTagResponse::onResponse::Success::" + response.body()!!.message+ "::" + response.body()!!.data)
                    results = response.body()!!.data

                    setRecyclerView()
                }
                else {
                    Log.e("SearchResultActivity::", "postSearchTagResponse::onResponse::Fail:: " + response.body()!!.message)
                }
            }
        })
    }
}