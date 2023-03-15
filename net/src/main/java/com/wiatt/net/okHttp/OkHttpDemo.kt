package com.wiatt.net

import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import com.wiatt.common.AppExecutors
import okhttp3.*
import okhttp3.Headers.Companion.headersOf
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okio.BufferedSink
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

object HttpTool {
    private val mBaseUrl = "https://www.baidu.com/"
    // OkHttpClient保持单例，每一个OkHttpClient都拥有自己的链接池和线程池
    // 通过 client.newBilder().build()的方式创建的新OkHttpClient与旧client共享连接池、线程池、配置信息
    private val client: OkHttpClient = OkHttpClient()
    private val gson = Gson()
    private val executor = AppExecutors.getInstance()

    fun doGetSync(callback: ResultCallback) {
        val builder = Request.Builder()
        val request: Request = builder.url(mBaseUrl).build()
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        var responseHeaders: Headers = response.headers
        for (i in 0 until responseHeaders.size) {
            println("headers: ${responseHeaders.name(i)}: ${responseHeaders.value(i)};")
        }
        // 小文档可以通过string的方式全部加载到内存中
        // 但是大于1M的文档，就必须使用io流的方式解析: response.body()?.byteStream()
        println("content: ${response.body?.string()}")
        // 下载大文件使用io流
        /*val inputStream: InputStream? = response.body()?.byteStream()*/
    }

    fun doGetAsyn(callback: ResultCallback) {
        client.newBuilder().eventListener(object : EventListener() {

        })
        val builder = Request.Builder()
        val request: Request = builder.get().url(mBaseUrl).build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                var res: String? = response.body?.string()
                // 下载大文件使用io流
                /*val inputStream: InputStream? = response.body()?.byteStream()*/
                callback.onSucess(res ?: "")
            }
        })

    }

    fun doPostStr(callback: ResultCallback) {
        val requestBody = RequestBody.create("text/plain;charset=utf-8".toMediaTypeOrNull(), "username:wiatt, password: 123");
        val builder: Request.Builder = Request.Builder()
        val request: Request = builder
                .url("${mBaseUrl}postString")
                .post(requestBody)
                .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val res: String? = response.body?.string()
                // 下载大文件使用io流
                /*val inputStream: InputStream? = response.body()?.byteStream()*/
                callback.onSucess(res ?: "")
            }
        })
    }

    fun doPostStream(callback: ResultCallback) {
        val MEDIA_TYPE_MARKDOWN = "text/x-markdown; charset=utf-8".toMediaTypeOrNull()
        val requestBody = object : RequestBody() {
            override fun contentType(): MediaType? {
                return MEDIA_TYPE_MARKDOWN
            }

            override fun writeTo(sink: BufferedSink) {
                sink.writeUtf8("Numbers\n")
                sink.writeUtf8("-------\n")
                for (i in 2..997) {
                    sink.writeUtf8(String.format("* ${factor(i)} = $i\n"))
                }
            }

            private fun factor(n: Int): String {
                for (i in 2 until n) {
                    var x = n / i
                    if (x * i == n) {
                        return "${factor(x)} x $i"
                    }
                }
                return n.toString()
            }
        }

        val request: Request = Request.Builder()
                .url(mBaseUrl)
                .post(requestBody)
                .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        // 下载大文件使用io流
        /*val inputStream: InputStream? = response.body()?.byteStream()*/
        println("response: ${response.body?.string()}")
    }

    fun doPostFile(callback: ResultCallback) {
        val file = File(Environment.getExternalStorageState(), "banner.png")
        if (!file.exists()) {
            return
        }
        val requestBody = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), file)
        val builder = Request.Builder();
        val request = builder.url("${mBaseUrl}postFile").post(requestBody).build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body?.string()
                // 下载大文件使用io流
                /*val inputStream: InputStream? = response.body()?.byteStream()*/
                callback.onSucess(res ?: "")
            }

        })
    }

    fun doPostForm(callback: ResultCallback) {
        val requestBodyBuilder: FormBody.Builder = FormBody.Builder()
        val requestBody = requestBodyBuilder
                .add("userName", "AI")
                .add("password", "123456")
                .build()
        val builder: Request.Builder = Request.Builder()
        val request: Request = builder
                .url("${mBaseUrl}login")
                .post(requestBody)
                .build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body?.string()
                // 下载大文件使用io流
                /*val inputStream: InputStream? = response.body()?.byteStream()*/
                callback.onSucess(res ?: "")
            }

        })
    }

    fun doPostMultipart(callback: ResultCallback) {
        val file = File(Environment.getExternalStorageState(), "uz_splash_bg.png");
        if (!file.exists()) {
            return
        }
        val multipartBuilder = MultipartBody.Builder()
        val requestBody = multipartBuilder.setType(MultipartBody.FORM)
                .addPart(
                    headersOf(
                        "Content-Disposition",
                        "form-data;name=\"username\""
                    ),
                        RequestBody.create(null, "wiatt"))
                .addPart(
                    headersOf(
                        "Content-Disposition",
                        "form-data; name=\"password\""
                    ),
                        RequestBody.create(null, "123"))
                .addFormDataPart("mPhoto", "wiatt.png",
                        RequestBody.create("application/octet-stream".toMediaTypeOrNull(), file))
                .build()
        val builder: Request.Builder = Request.Builder()
        val request = builder.url("${mBaseUrl}uploadInfo")
                .post(requestBody)
                .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body?.string()
                // 下载大文件使用io流
                /*val inputStream: InputStream? = response.body()?.byteStream()*/
                callback.onSucess(res ?: "")
            }

        })
    }

    fun getSetHead() {
        val request = Request.Builder().url(mBaseUrl)
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build()
        val response: Response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        println("Server: ${response.header("Server")}")
        println("Date: ${response.header("Date")}")

        println("Vary: ${response.headers("Vary")}")
    }

    fun jsonParse() {
        val request = Request.Builder()
                .url(mBaseUrl)
                .build()
        val response: Response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        val gist: Gist = gson.fromJson(response.body?.charStream(), Gist::class.java)
        for (entry in gist.files.entries) {
            println(entry.key)
            println(entry.value.content)
        }
    }

    fun cacheResponse(cacheDirectory: File) {
        fun setCache(cacheDirectory: File) {
            val cacheSize = 10 * 1024 * 1024
            val cache = Cache(cacheDirectory, cacheSize.toLong())
            client.newBuilder().cache(cache).build()
        }

        setCache(cacheDirectory)
        val request = Request.Builder().url(mBaseUrl).build()
        val response1 = client.newCall(request).execute()
        if (!response1.isSuccessful) throw IOException("Unexpected code $response1")
        val response1BodyStr = response1.body?.string()
        println("Response 1 response: $response1")
        println("Response 1 cache response: ${response1.cacheResponse}")
        println("Response 1 network response: ${response1.networkResponse}")

        val response2 = client.newCall(request).execute()
        if (!response1.isSuccessful) throw IOException("Unexpected code $response1")
        val response2BodyStr = response2.body?.string()
        println("Response 2 response: $response2")
        println("Response 2 cache response: ${response2.cacheResponse}")
        println("Response 2 network response: ${response2.networkResponse}")

        println("Response 2 equals Response 1? ${response1BodyStr.equals(response2BodyStr)}")
    }

    fun cacheCall() {
        val request = Request.Builder().url(mBaseUrl).build()
        val startNanos = System.nanoTime()
        val call = client.newCall(request)
        executor.scheduledExecutor().schedule({
            println("${(System.nanoTime() - startNanos) / 1e9f} Canceling call.")
            call.cancel()
            println("${(System.nanoTime() - startNanos) / 1e9f} Canceled call.")
        }, 1, TimeUnit.SECONDS)

        try {
            println("${(System.nanoTime() - startNanos) / 1e9f} Executing call.")
            val response = call.execute()
            println("${(System.nanoTime() - startNanos) / 1e9f}, Call was expected to fail, but completed.")
        } catch (e: IOException) {
            println("${(System.nanoTime() - startNanos) / 1e9f} Call failed as expected.")
        }
    }

    fun setOutTime() {
        val clientCopy = client.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

        val request = Request.Builder().url(mBaseUrl).build()
        val response = clientCopy.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        println("Response completed: $response")
    }

    fun changeConfigForSingle() {
        val request = Request.Builder().url(mBaseUrl).build()

        try {
            val copyClient1 = client.newBuilder()
                    .readTimeout(500, TimeUnit.MILLISECONDS)
                    .build()
            val response = copyClient1.newCall(request).execute()
            println("response 1 succeeded: $response")
        } catch (e: IOException) {
            println("response 1 failed: $e")
        }

        try {
            val copyClient2 = client.newBuilder()
                    .readTimeout(3000, TimeUnit.MILLISECONDS)
                    .build()
            val response = copyClient2.newCall(request).execute()
            println("response 2 succeeded: $response")
        } catch (e: IOException) {
            println("response 2 failed: $e")
        }
    }
    // 全局interceptor，该类interceptor在整个拦截器链中最早被调用，
    // 通过OkHttpClient.Builder#addInterceptor(Interceptor)传入

    //非网页请求的 interceptor，这类拦截器只会在非网页请求中被调用，并且是在组装完请求之后，真正发起网络请求前被调用，
    // 所有的 interceptor 被保存在List interceptors 集合中，按照添加顺序来逐个调用，
    // 通过OkHttpClient.Builder#addNetworkInterceptor(Interceptor) 传入
    fun setInterceptor() {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .build()
        val request = Request.Builder()
            .url(mBaseUrl)
            .build()
        okHttpClient.newCall(request).execute()
    }

    /**
     * 自动更新鉴权的操作
     * 已经发送了一次请求，但是token过期，此时会走到authenticate方法中
     */
    fun setAuthenticator() {
        client.newBuilder()
            .authenticator(object : Authenticator {
                override fun authenticate(route: Route?, response: Response): Request? {
                // 刷新token
                val request: Request = response.request.newBuilder().header("Authorinzation", "xxx").build()
                return request
                }
            })
            .build()
    }
}


data class Gist(var files: Map<String, GistFile>)

data class GistFile(var content: String)

class LoggingInterceptor: Interceptor {
    private val TAG = "LoggingInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startTime = System.nanoTime()
        Log.d(TAG, String.format("Sending requeset ${request.url} on ${chain.connection()}、${request.headers}"))
        var response = chain.proceed(request)
        val endTime = System.nanoTime()
        Log.d(TAG, String.format("Received response for ${response.request.url} in ${endTime - startTime}、${response.headers}"))
        return response
    }

}

abstract class ResultCallback {
    abstract fun onFail(failMsg: String)
    abstract fun onSucess(result: String)


}