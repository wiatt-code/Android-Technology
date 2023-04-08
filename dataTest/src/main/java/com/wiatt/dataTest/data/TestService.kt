package com.wiatt.dataTest.data

import com.wiatt.net.retrofit.converter.RequestFormate
import com.wiatt.net.retrofit.converter.ResponseFormate
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Path

interface TestService {
    @GET("users/{user}/repos")
    @RequestFormate("json")
    @ResponseFormate("json")
    fun getRepos(@Path("user") user: String): Call<List<Repo>>

    @GET("users/{user}")
    @RequestFormate("json")
    @ResponseFormate("json")
    fun getOwner(@Path("user") user: String): Call<OwnerMain>

    /**
     * method 表示请求的方法，区分大小写
     * path表示路径
     * hasBody表示是否有请求体
     */
    @HTTP(method = "GET", path = "blog/{id}", hasBody = false)
    fun getBlog(@Path("id") id: Int): Call<ResponseBody>
}