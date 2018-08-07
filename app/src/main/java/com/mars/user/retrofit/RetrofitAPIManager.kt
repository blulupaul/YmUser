package com.mars.user.retrofit

import android.util.Log
import com.mars.user.api.ApiService
import com.mars.user.constant.Url
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by gu on 2017/03/25
 * ━━━━━━女神出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    女神保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 *
 *
 * ━━━━━━感觉萌萌哒━━━━━━
 */
object RetrofitAPIManager {

    private val DEFAULT_TIMEOUT = 200L
    private var apiService: ApiService? = null
    private var apiServicePush: ApiService? = null

    private var retrofit: Retrofit? = null
    private var retrofitPush: Retrofit? = null

    private fun provideClientApi(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(Url.BASE_URL_NEW)
                    .client(genericClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
        return retrofit!!
    }

    private fun provideClientApiPush(): Retrofit {
        if (retrofitPush == null) {
            retrofitPush = Retrofit.Builder()
                    .baseUrl(Url.BASE_URL_PUSH_NEW)
                    .client(genericClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
        return retrofitPush!!
    }

    fun getProvideClientApi(): ApiService {
        if (apiService == null) {
            apiService = provideClientApi().create(ApiService::class.java)
        }

        return apiService!!
    }

    fun getProvideClientApiPush(): ApiService {
        if (apiServicePush == null) {
            apiServicePush = provideClientApiPush().create(ApiService::class.java)
        }

        return apiServicePush!!
    }

    private fun genericClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            //打印retrofit日志
            Log.i("RetrofitLog", "retrofitBack = $message")
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request()
                            .newBuilder()
                            .addHeader("Authorization", "Basic ZG9iZXN0OmRiMTIzNDU2")
                            .build()
                    chain.proceed(request)
                }
                .addInterceptor(loggingInterceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build()
    }
}
