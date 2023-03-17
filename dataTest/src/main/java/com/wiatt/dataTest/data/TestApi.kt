package com.wiatt.dataTest.data

import com.wiatt.net.retrofit.RetrofitClient
import retrofit2.Call

class TestApi {

    fun getRepos(userName: String): Call<List<Repo>> {
        return RetrofitClient.instance.creat(TestService::class.java)
            .getRepos(userName)
    }
}