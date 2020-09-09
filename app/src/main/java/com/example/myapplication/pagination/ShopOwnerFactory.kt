package com.example.myapplication.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.myapplication.model.ShopOwners

class ShopOwnerFactory  : DataSource.Factory<Int, ShopOwners>() {
    val userLiveDataSource = MutableLiveData<ShopOwnerPagination>()
    override fun create(): DataSource<Int, ShopOwners> {
        val userDataSource = ShopOwnerPagination()
        userLiveDataSource.postValue(userDataSource)
        return userDataSource
    }
}