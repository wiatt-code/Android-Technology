package com.wiatt.net.retrofit.converter

import java.lang.annotation.RetentionPolicy

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ResponseFormate(val value: String = "") {
    companion object {
        const val JSON = "json"
        const val XML = "xml"
    }
}
