package com.wiatt.net.retrofit.converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jaxb.JaxbConverterFactory
import java.lang.reflect.Type

/**
 * 自定义转换器
 */
class JsonOrXmlConverterFactory private constructor(): Converter.Factory() {
    private val gsonFactory: Converter.Factory = GsonConverterFactory.create()
    private val jaxbFactory: Converter.Factory = JaxbConverterFactory.create()
    private val gson = Gson()
    companion object {
        public fun create(): JsonOrXmlConverterFactory {
            return JsonOrXmlConverterFactory()
        }
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        for (annotation: Annotation in methodAnnotations) {
            if (annotation !is RequestFormate) {
                continue
            }
            val value = (annotation as RequestFormate).value
            if (value == RequestFormate.JSON) {
                return gsonFactory.requestBodyConverter(
                    type,
                    parameterAnnotations,
                    methodAnnotations,
                    retrofit
                )
            } else if (value == RequestFormate.XML){
                return jaxbFactory.requestBodyConverter(
                    type,
                    parameterAnnotations,
                    methodAnnotations,
                    retrofit
                )
            }
        }
        return super.requestBodyConverter(
            type,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        )
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        for (annotation in annotations) {
            if (annotation !is ResponseFormate) {
                continue
            }
            val value = annotation.value
            if (value == ResponseFormate.JSON) {
                val adapter = gson.getAdapter(TypeToken.get(type))
                return MyGsonResponseBodyConverter(gson, adapter)
            } else if (value == ResponseFormate.XML) {
                return jaxbFactory.responseBodyConverter(type, annotations, retrofit)
            }
        }
        return super.responseBodyConverter(type, annotations, retrofit)
    }
}