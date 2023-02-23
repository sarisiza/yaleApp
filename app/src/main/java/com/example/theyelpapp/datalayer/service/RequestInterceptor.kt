package com.example.theyelpapp.datalayer.service

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RequestInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().newBuilder().apply {
            addHeader(
                "Authorization",
                "Bearer yK4_qLC-Yxz_JFx_IHKYN0AlB3XF3xBVlY8k6HyyRzMOb-pclA4LaCONVFOs9EaLAxRtE8dawSCj37Llj6Z9EI0HVj6A0MalySij8fJ_cySYMLMFlrFRmhlNr5vxY3Yx"
            )
        }.build().also { return chain.proceed(it) }
    }
}