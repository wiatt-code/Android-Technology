package com.wiatt.dataTest.data

import com.wiatt.net.retrofit.RetrofitClient
import retrofit2.Call

class TestApi {

    fun getOwner(userName: String): Call<OwnerMain> {
        return RetrofitClient.instance.creat(TestService::class.java)
            .getOwner(userName)
    }

    fun getRepos(userName: String): Call<List<Repo>> {
        return RetrofitClient.instance.creat(TestService::class.java)
            .getRepos(userName)
    }
}