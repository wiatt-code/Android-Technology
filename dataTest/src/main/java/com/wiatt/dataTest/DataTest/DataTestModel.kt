package com.wiatt.dataTest.DataTest

import com.wiatt.common.mvp.BaseModel
import com.wiatt.dataTest.data.Repo
import com.wiatt.dataTest.data.TestApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataTestModel(override var p: DataTestPresenter) :
        BaseModel<DataTestModel, DataTestPresenter, DataTestContract.IDtIModel>(p) {
    override fun getContract(): DataTestContract.IDtIModel {
        return DtModel()
    }

    inner class DtModel: DataTestContract.IDtIModel {
        override fun requestRepoFromNet(userName: String) {

            TestApi().getRepos("octocat").enqueue(object : Callback<List<Repo>> {
                override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                    println("请求成功")
                    val repoStr = response.body()?.get(0)?.toString()
                    if (repoStr != null) {
                        p.getContract().responseRepoFromNet(repoStr)
                    }
                }

                override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                    println("请求失败")
                    p.getContract().responseRepoFromNet("请求失败")
                }
            })

        }

        override fun requestRepoFromDataBase() {
            p.getContract().responseRepoFromDataBase(arrayListOf<Repo>())
        }

        override fun requestRepoFromFile() {
            p.getContract().responseRepoFromFile(arrayListOf<Repo>())
        }

    }
}