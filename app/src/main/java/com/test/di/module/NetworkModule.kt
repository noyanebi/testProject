package com.test.di.module

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.BuildConfig
import com.test.data.mapper.ProductMapper
import com.test.data.remote.APIService
import com.test.data.remote.errorhandle.ErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val TAG = NetworkModule::class.java.name
    private val TIME_OUT = 60L
    private val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

    @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        if (BuildConfig.DEBUG)
            client.addNetworkInterceptor(interceptor)

        client.addInterceptor { chain ->
            var request = chain.request()
            val url = request.url.newBuilder()
                .build()
            request = request.newBuilder().url(url).build();
            chain.proceed(request)
        }

        client.addInterceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                .headers(getJsonHeader())

            val response = chain.proceed(requestBuilder.build())
            if (response.code == 401) {
                val newRequest = response.request
                    .newBuilder()
                    .headers(getJsonHeader())
                    .build()

                return@addInterceptor chain.proceed(newRequest)
            }

            response
        }

        return client.build()
    }

    private fun getJsonHeader(): Headers {
        val builder = Headers.Builder()
        builder.add("Content-Type", "application/json")
        builder.add("x-api-key", BuildConfig.API_KEY)

        return builder.build()
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().setDateFormat(DATE_FORMAT).create()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun providesErrorHandler(): ErrorHandler {
        return ErrorHandler()
    }


    @Provides
    fun provideProductMapper() = ProductMapper()


    @Provides
    @Singleton
    fun provideIsNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }
}