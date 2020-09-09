package com.example.myapplication.pagination

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.myapplication.model.GetShopDetails
import com.example.myapplication.model.ShopOwners
import com.example.myapplication.network.ApiNetwork
import com.example.myapplication.network.ApiService
import com.example.myapplication.network.NetworkServiceForShoplist
import com.example.myapplication.utill.State
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShopOwnerListPagination(
    private val networkService: NetworkServiceForShoplist,
    private val compositeDisposables: CompositeDisposable
) : PageKeyedDataSource<Int, ShopOwners>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null
    val FIRST_PAGE = 1
    var shopownerlist: List<ShopOwners>? = null
    var authtoken =
        "jwt eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlclR5cGUiOiJTVVBFUkFETUlOIiwiaWF0IjoxNTg2MjU4NTA4fQ.PfjEdI53hsOf1ihk8bNeelR9tV6DTGVhsF1vZNCe5BU"


    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposables.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()

            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ShopOwners>) {
        updateState(State.LOADING)
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
                    responseItems?.let {
                        callback.onResult(responseItems, key)
                    }
                }
            }

            override fun onFailure(call: Call<GetShopDetails>, t: Throwable) {
                Log.d("TAG", "onFailure: " + t.message)
            }

            /* if (response.isSuccessful) {
                 if (response.code() == 200 && response.body() != null) {
                     if (response.body() != null) {
                         if (!response.body()!!.boolean) {
                             val apiResponse = response.body()!!
                             updateState(State.DONE)
                             val responseItems = apiResponse.shopOwners
                             val key = params.key + 1
                             responseItems.let {
                                 callback.onResult(responseItems as List<ShopOwners>, key)
                             }
                         }


                     }
                 }
             }*/


        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ShopOwners>) {


    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ShopOwners>
    ) {
        updateState(State.LOADING)
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
                /* if (response.isSuccessful) {
                     if (response.code() == 200 && response.body() != null) {
                         if (response.body() != null) {
                             if (!response.body()!!.boolean) {
                                 val apiResponse = response.body()!!
                                 val responseItems = apiResponse.shopOwners
                                 updateState(State.DONE)
                                 responseItems.let {
                                     callback.onResult(
                                         responseItems as List<ShopOwners>,
                                         null,
                                         FIRST_PAGE + 1
                                     )
                                     shopownerlist = responseItems as List<ShopOwners>?
                                 }
                             }
                         }
                     }
                 }*/
            }

            override fun onFailure(call: Call<GetShopDetails>, t: Throwable) {
                Log.d("TAG", "onFailure: " + t.message)
            }
        })

    }
    companion object{
        const val PAGE_SIZE = 6
        const val FIRST_PAGE = 1
    }
}


