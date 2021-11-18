package com.borja.pruebanewyorktimes.di

import com.borja.pruebanewyorktimes.data.remote.Service
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideGsonServer(): Gson = GsonBuilder().create()

    @Provides
    @Named("okHttpClient")
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder =
        HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().addInterceptor(this)
        }

    @Provides
    @Named("retrofitClient")
    fun provideRetrofit(
        @Named("okHttpClient") okHttpClientBuilder: OkHttpClient.Builder,
        gson: Gson
    ): Retrofit =
        Retrofit.Builder().baseUrl("https://api.nytimes.com/svc/mostpopular/v2/")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideService(@Named("retrofitClient") retrofit: Retrofit): Service =
        retrofit.create(Service::class.java)
}