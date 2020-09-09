package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myapplication.model.ShopOwners
import com.example.myapplication.pagination.ShopOwnerDataSourceFActory
import com.example.myapplication.pagination.ShopOwnerFactory
import com.example.myapplication.pagination.ShopOwnerListPagination
import com.example.myapplication.pagination.ShopOwnerPagination

class ShopOwnerViewModel:ViewModel() {

    var userPagedList: LiveData<PagedList<ShopOwners>>
    private var liveDataSource: LiveData<ShopOwnerPagination>
    init {
        val itemDataSourceFactory = ShopOwnerFactory()
        liveDataSource = itemDataSourceFactory.userLiveDataSource
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(ShopOwnerListPagination.PAGE_SIZE)
            .build()
        userPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()
    }
}