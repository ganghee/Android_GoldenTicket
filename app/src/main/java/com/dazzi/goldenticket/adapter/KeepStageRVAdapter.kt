package com.dazzi.goldenticket.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.adapter.viewholder.KeepPosterHoder
import com.dazzi.goldenticket.data.KeepShowData
import com.dazzi.goldenticket.db.SharedPreferenceController
import com.dazzi.goldenticket.network.Controller
import com.dazzi.goldenticket.network.NetworkService
import com.dazzi.goldenticket.network.delete.DeleteShowLikeResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KeepStageRVAdapter : RecyclerView.Adapter<KeepPosterHoder>() {
    val networkService: NetworkService by lazy {
        Controller.instance.networkService
    }
    var token = ""
    val keepPosterList = mutableListOf<KeepShowData>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): KeepPosterHoder =
        KeepPosterHoder(LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_keep_item, viewGroup, false))

    override fun getItemCount() = keepPosterList.size

    override fun onBindViewHolder(holder: KeepPosterHoder, position: Int) {

        token = SharedPreferenceController.getUserToken(holder.btnLike.context)

        holder.bind(keepPosterList[position])
        holder.btnLike.onClick {
            deleteKeepShowResponse(position)
            keepPosterList.removeAt(position)
            notifyItemRemoved(position)
            Toast.makeText(holder.btnLike.context, "삭제가 되었습니다.", Toast.LENGTH_LONG).show()
        }
    }

    fun setData(setKeepPosterList: List<KeepShowData>) {
        keepPosterList.clear()
        keepPosterList.addAll(setKeepPosterList)
        notifyDataSetChanged()
    }


    private fun deleteKeepShowResponse(position: Int) {
        val jsonObject = JSONObject()
        jsonObject.put("show_idx", keepPosterList[position].show_idx)
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val deleteKeepPoster = networkService.deleteShowLike(
            "application/json",
            token,
            gsonObject
        )
        deleteKeepPoster.enqueue(object : Callback<DeleteShowLikeResponse> {
            override fun onFailure(call: Call<DeleteShowLikeResponse>, t: Throwable) {
                Log.e("Delete ShowLike Failed:", t.toString())
            }

            override fun onResponse(
                call: Call<DeleteShowLikeResponse>,
                response: Response<DeleteShowLikeResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("TEST", response.body()!!.toString())
                    if (response.body()!!.status == 200) {
                        Log.d("keepPosterList", "@@@@@$keepPosterList")
                    }
                }
            }
        })
    }
}
