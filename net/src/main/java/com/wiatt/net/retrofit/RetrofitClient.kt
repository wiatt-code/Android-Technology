package com.wiatt.net.retrofit

import com.wiatt.net.BuildConfig
import com.wiatt.net.NetApplication
import com.wiatt.net.retrofit.converter.JsonOrXmlConverterFactory
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File
import java.lang.Exception
import java.net.URLDecoder
import java.util.concurrent.TimeUnit

private const val DEFAULT_CONNECT_TIMEOUT = 10
private const val DEFAULT_READ_TIMEOUT = 10
private const val DEFAULT_WRITE_TIMEOUT = 10
private const val DEFAULT_URL = "https://api.github.com/"

class RetrofitClient private constructor(){
    companion object {
        val instance: RetrofitClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitClient()
        }
    }

    private var baseUrl = DEFAULT_URL
    private var enableCache: Boolean = false
    private var cache: Cache? = null
    private val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger{
        override fun log(message: String) {
            if (BuildConfig.DEBUG) {
                val text = URLDecoder.decode(message, "utf-8")

            }
        }
    })
    private var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(DEFAULT_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .readTimeout(DEFAULT_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .writeTimeout(DEFAULT_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            // 连接池设置
        .connectionPool(ConnectionPool(5, 10, TimeUnit.SECONDS))
        .build()
    private var retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(JsonOrXmlConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    fun enableCache(enable: Boolean): RetrofitClient {
        if (enableCache != enable) {
            enableCache = enable
            if (enable) {
                var cache = initCache()
                okHttpClient = okHttpClient.newBuilder().cache(cache).build()
                retrofit = retrofit.newBuilder().client(okHttpClient).build()
            } else {
                okHttpClient = okHttpClient.newBuilder().cache(null).build()
                retrofit = retrofit.newBuilder().client(okHttpClient).build()
            }
        }
        return this
    }

    private fun initCache(): Cache?{
        return try {
            if (cache == null) {
                val cacheDir = File(NetApplication.mApplication?.cacheDir, "cache")
                cache = Cache(cacheDir, (10 * 1024 * 1024).toLong())
            }
            cache
        } catch (e: Exception){

            null
        }
    }

    fun updateBaseUrl(url: String): RetrofitClient {
        if (baseUrl != url) {
            baseUrl = url
            retrofit = retrofit.newBuilder().baseUrl(baseUrl).build()
        }
        return this
    }

    fun <T> creat(service: Class<T>, url: String? = null): T {
        return if (url == null) {
            retrofit.create(service)
        } else {
            val tmpRetrofit = retrofit.newBuilder().baseUrl(url).build()
            tmpRetrofit.create(service)
        }
    }
}