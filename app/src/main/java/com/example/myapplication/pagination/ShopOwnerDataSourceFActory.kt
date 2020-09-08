package com.example.myapplication.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.myapplication.model.ShopOwners
import com.example.myapplication.network.NetworkService
import com.example.myapplication.network.NetworkServiceForShoplist
import io.reactivex.disposables.CompositeDisposable

class ShopOwnerDataSourceFActory(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkServiceForShoplist
) : DataSource.Factory<Int, ShopOwners>() {

    val newsDataSourceLiveData = MutableLiveData<ShopOwnerListPagination>()

    override fun create(): DataSource<Int, ShopOwners> {
        val newsDataSource = ShopOwnerListPagination(networkService, compositeDisposable)
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}