package com.example.myapplication.network

import com.example.myapplication.model.GetShopDetails
import com.example.myapplication.model.Response
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface NetworkServiceForShoplist {

    @FormUrlEncoded
    @POST("admin/getShopOwnerList")
    fun getShopDetails(
        @Header("Authorization") auth: String?,
        @Field("pageNumber") pageNumber: Int
    ): Call<GetShopDetails>

    companion object {
        fun getService(): NetworkService {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://188.166.228.50:3006/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(NetworkService::class.java)
        }
    }
}