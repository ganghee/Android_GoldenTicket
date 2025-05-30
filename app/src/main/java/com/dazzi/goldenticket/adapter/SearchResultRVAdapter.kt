package com.dazzi.goldenticket.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dazzi.goldenticket.activity.StageInfoActivity
import com.dazzi.goldenticket.db.SharedPreferenceController
import com.dazzi.goldenticket.network.Controller
import com.dazzi.goldenticket.network.NetworkService
import com.dazzi.goldenticket.network.delete.DeleteShowLikeResponse
import com.dazzi.goldenticket.network.post.PostShowLikeResponse
import com.dazzi.goldenticket.network.post.SearchData
import com.dazzi.goldenticket.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import org.jetbrains.anko.startActivity

class SearchResultRVAdapter(val ctx: Context, val dataList: ArrayList<SearchData>): RecyclerView.Adapter<SearchResultRVAdapter.Holder>() {
    val networkService: NetworkService by lazy {
        Controller.instance.networkService
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_keep_item,viewGroup,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(ctx)
            .load(dataList[position].image_url)
            .into(holder.searchResultImgUrl)

        Log.e("SearchResultRVAdtr::", "onBindViewHolder::is_liked" + dataList[position].is_liked)

        when (dataList[position].is_liked) {
            0 -> {
                holder.searchResultLike.isSelected = false
                holder.searchResultLike.setBackgroundResource(R.drawable.icon_like_nofill)
            }
            1 -> {
                holder.searchResultLike.isSelected = true
                holder.searchResultLike.setBackgroundResource(R.drawable.icon_like_fill)
            }
        }

        holder.itemView.setOnClickListener {
            //해당 포지션의 show_idx를 stageinfo의 path variable로 전달
            Log.e("SearchResultRVAdtr::", "onCreateViewHolder::setOnClickListener::" + dataList[position].show_idx)
            ctx.startActivity<StageInfoActivity>("idx" to dataList[position].show_idx)
        }

        holder.searchResultLike.setOnClickListener {

            /** 좋아요 취소 **/
            if(holder.searchResultLike.isSelected){
                val token = SharedPreferenceController.getUserToken(ctx)

                val jsonObject = JSONObject()
                jsonObject.put("showIdx", dataList[position].show_idx)
                val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

                val deleteShowLike = networkService.deleteShowLike("application/json", token, gsonObject)
                deleteShowLike.enqueue(object: Callback<DeleteShowLikeResponse> {
                    override fun onFailure(call: Call<DeleteShowLikeResponse>, t: Throwable) {
                        Log.e("Delete ShowLike Failed:",t.toString())
                    }

                    override fun onResponse(
                        call: Call<DeleteShowLikeResponse>,
                        response: Response<DeleteShowLikeResponse>
                    ) {
                        if(response.isSuccessful){
                            Log.e("SearcyResultRVAdtr::", "like_click_listener::취소::onResponse" + response.body()!!.message)
                            if(response.body()!!.status == 200){
                                // 통신 완료된 후 no fill like로 변경
                                holder.searchResultLike.isSelected = !holder.searchResultLike.isSelected
                                holder.searchResultLike.setBackgroundResource(R.drawable.icon_like_nofill)
                            }
                        }
                    }

                })
            }
            /** 좋아요 **/
            else{
                val token = SharedPreferenceController.getUserToken(ctx)

                val jsonObject = JSONObject()
                jsonObject.put("showIdx", dataList[position].show_idx) // TODO: 인덱스 값 수정해야함
                val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

                val postShowLike = networkService.postShowLike("application/json", token, gsonObject)
                postShowLike.enqueue(object: Callback<PostShowLikeResponse> {
                    override fun onFailure(call: Call<PostShowLikeResponse>, t: Throwable) {
                        Log.e("INSERT ShowLike Failed:",t.toString())
                    }

                    override fun onResponse(
                        call: Call<PostShowLikeResponse>,
                        response: Response<PostShowLikeResponse>
                    ) {
                        if(response.isSuccessful){
                            Log.e("SearcyResultRVAdtr::", "like_click_listener::조하::onResponse" + response.body()!!.message)
                            if(response.body()!!.status == 200){
                                // 통신 완료된 후 fill like로 변경
                                holder.searchResultLike.isSelected = !holder.searchResultLike.isSelected
                                holder.searchResultLike.setBackgroundResource(R.drawable.icon_like_fill)
                            }
                        }
                    }

                })
            }
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val searchResultImgUrl = itemView.findViewById(R.id.ivKeepStage) as ImageView
        val searchResultLike = itemView.findViewById(R.id.ibtnKeepShowLike) as ImageButton
    }
}