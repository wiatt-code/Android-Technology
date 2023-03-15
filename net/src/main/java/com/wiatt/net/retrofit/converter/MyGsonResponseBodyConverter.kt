package com.wiatt.net.retrofit.converter

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonToken
import okhttp3.ResponseBody
import retrofit2.Converter

/**
 * 自定义gson格式的响应体转换器
 * 主要用于演示自定义该转换器，convert内容与retrofit提供的一模一样
 */
class MyGsonResponseBodyConverter<T> constructor(val gson: Gson, val adapter: TypeAdapter<T>): Converter<ResponseBody, T> {

    override fun convert(value: ResponseBody): T? {
        val jsonReader = gson.newJsonReader(value.charStream())
        return value.use {
            val result = adapter.read(jsonReader)
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }
            result
        }
    }
}