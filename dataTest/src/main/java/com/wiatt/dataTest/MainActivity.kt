package com.wiatt.dataTest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.wiatt.dataTest.data.Repo
import com.wiatt.dataTest.data.TestApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Route(path = "/dataTest/MainActivity")
class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvResult = findViewById(R.id.tvResult)
    }

    fun getInfo(view: View) {
        TestApi().getRepos("octocat").enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                println("请求成功")
                val repoStr = response.body()?.get(0)?.toString()

                runOnUiThread {
                    tvResult.text = "请求成功，repo = $repoStr"
                }
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                println("请求失败")
            }
        })
    }
}

