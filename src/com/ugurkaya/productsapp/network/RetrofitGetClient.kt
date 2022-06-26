package com.ugurkaya.productsapp.network





import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitGetClient {

    val BASE_URL: String = "https://mocki.io/v1/"
    val HEADER_CACHE_CONTROL = "Cache-Control"
    private var instance: RetrofitGetClient? = null
    fun getInstance(): RetrofitGetClient? {
        if (instance == null) {
            instance = RetrofitGetClient()
        }
        return instance
    }

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(networkInterceptor()) // only used when network is on
            .addInterceptor(offlineInterceptor())
            .build()
    }


    private fun offlineInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            chain.proceed(request)
        }
    }

    private fun networkInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val cacheControl: CacheControl = CacheControl.Builder()
                .maxAge(1, TimeUnit.SECONDS)
                .build()
            response.newBuilder()
                .removeHeader(HEADER_CACHE_CONTROL)
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
        }
    }

    fun getApi(): APIInterface {
        return retrofit().create(APIInterface::class.java)
    }


}

