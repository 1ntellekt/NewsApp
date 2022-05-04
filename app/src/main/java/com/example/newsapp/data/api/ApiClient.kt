package com.example.newsapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient private constructor() {

    companion object{
        private const val BASE_URL = "https://newsapi.org/v2/"
        private var apiClient:ApiClient?=null
        private var retrofit:Retrofit?=null

        @Synchronized
        fun getInstance():ApiClient{
            if (apiClient == null) {
                apiClient = ApiClient()
            }
            return apiClient!!
        }
    }

    init {
        retrofit =  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApi():ApiInterface{
        return retrofit!!.create(ApiInterface::class.java)
    }

}