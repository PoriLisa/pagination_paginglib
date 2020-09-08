package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class GetShopDetails ( val  boolean: Boolean,var statuscode:Float,var pages:Int ,@SerializedName("shopOwners") val shopOwners:List<ShopOwners>)
