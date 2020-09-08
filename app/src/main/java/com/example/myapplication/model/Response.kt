package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class Response(@SerializedName("articles") val news: List<News>)


