package com.example.myapplication.network
import com.example.myapplication.model.GetShopDetails
import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    @FormUrlEncoded
    @POST("admin/getShopOwnerList")
    fun getShopDetails(
        @Header("Authorization") auth: String?,
        @Field("pageNumber") pageNumber: Int
    ): Call<GetShopDetails>

}