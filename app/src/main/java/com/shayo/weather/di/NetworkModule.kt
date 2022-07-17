package com.shayo.weather.di

import com.shayo.weather.BuildConfig.WEATHER_API_KEY
import com.shayo.weather.data.weather.network.WeatherApi
import com.shayo.weather.utils.runInDebug
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideGsonFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideAuthorizationInterceptor() = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            if (WEATHER_API_KEY.isBlank()) return chain.proceed(originalRequest)

            val new = originalRequest.newBuilder().url(originalRequest.url)
                .addHeader(WeatherApi.HEADER_AUTHORIZATION, WEATHER_API_KEY)
                .build()

            return chain.proceed(new)
        }
    }

    @Provides
    fun provideOKHTTPClient(
        authInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)

        runInDebug {
            client.addInterceptor(loggingInterceptor)
        }

        return client.build()
    }

    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gsonConverterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(WeatherApi.BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    fun provideNewsApi(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)
}



