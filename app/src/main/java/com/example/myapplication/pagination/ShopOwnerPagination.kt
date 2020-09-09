package com.example.myapplication.pagination

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.myapplication.model.GetShopDetails
import com.example.myapplication.model.ShopOwners
import com.example.myapplication.network.ApiNetwork
import com.example.myapplication.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopOwnerPagination : PageKeyedDataSource<Int, ShopOwners>() {
    var authtoken =
        "jwt eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlclR5cGUiOiJTVVBFUkFETUlOIiwiaWF0IjoxNTg2MjU4NTA4fQ.PfjEdI53hsOf1ihk8bNeelR9tV6DTGVhsF1vZNCe5BU"

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ShopOwners>) {
        val apiService = ApiNetwork.client.create(ApiService::class.java)
        val requestCall = apiService.getShopDetails(authtoken, params.key)

        requestCall.enqueue(object : Callback<GetShopDetails> {
            override fun onResponse(
                call: Call<GetShopDetails>,
                response: Response<GetShopDetails>
            ) {


                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.shopOwners
                    val key = params.key + 1
                    Log.d("TAG", "page: "+key)
                    responseItems.let {
                        callback.onResult(responseItems, key)
                    }
                }
            }

            override fun onFailure(call: Call<GetShopDetails>, t: Throwable) {
                Log.d("TAG", "onFailure: " + t.message)
            }
        })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ShopOwners>) {

    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ShopOwners>
    ) {
        val apiService = ApiNetwork.client.create(ApiService::class.java)
        val requestCall = apiService.getShopDetails(authtoken, FIRST_PAGE)

        requestCall.enqueue(object : Callback<GetShopDetails> {
            override fun onResponse(
                call: Call<GetShopDetails>,
                response: Response<GetShopDetails>
            ) {

                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.shopOwners
                    responseItems.let {
                        callback.onResult(responseItems, null, FIRST_PAGE + 1)

                    }
                }

            }

            override fun onFailure(call: Call<GetShopDetails>, t: Throwable) {
                Log.d("TAG", "onFailure: " + t.message)
            }
        })
    }

    companion object {
        const val PAGE_SIZE = 6
        const val FIRST_PAGE = 1
    }
}