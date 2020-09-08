package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myapplication.model.ShopOwners
import com.example.myapplication.network.NetworkServiceForShoplist
import com.example.myapplication.pagination.ShopOwnerDataSourceFActory
import com.example.myapplication.pagination.ShopOwnerListPagination
import com.example.myapplication.utill.State
import io.reactivex.disposables.CompositeDisposable

class ShopOwnerListViewModel() : ViewModel() {
    private val networkService = NetworkServiceForShoplist.getService()
    var shopownerlist: LiveData<PagedList<ShopOwners>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 5
    private val newsDataSourceFactory: ShopOwnerDataSourceFActory

    init {
        newsDataSourceFactory = ShopOwnerDataSourceFActory(compositeDisposable, networkService)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()


        shopownerlist = LivePagedListBuilder<Int, ShopOwners>(newsDataSourceFactory, config).build()
    }


    fun getState(): LiveData<State> = Transformations.switchMap<ShopOwnerListPagination,
            State>(newsDataSourceFactory.newsDataSourceLiveData, ShopOwnerListPagination::state)

    fun retry() {
        newsDataSourceFactory.newsDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return shopownerlist.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}