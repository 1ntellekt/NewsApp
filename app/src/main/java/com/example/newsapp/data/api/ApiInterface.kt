package com.example.newsapp.data.api

import com.example.newsapp.models.Headlines
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("top-headlines")
    fun getHeadlines(
        @Query("country")
        country:String?=null,
        @Query("apiKey")
        apiKey:String?=null
    ) : Call<Headlines>


}