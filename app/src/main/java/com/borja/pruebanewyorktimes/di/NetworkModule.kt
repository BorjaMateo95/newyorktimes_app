package com.borja.pruebanewyorktimes.di

import com.borja.pruebanewyorktimes.data.remote.Service
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideGsonServer(): Gson = GsonBuilder().create()


    @Provides
    @Named("retrofitClient")
    fun provideRetrofit(
        @Named("okHttpClient") okHttpClientBuilder: OkHttpClient.Builder,
        gson: Gson
    ): Retrofit =
        Retrofit.Builder().baseUrl("")
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideService(@Named("retrofitClient") retrofit: Retrofit): Service =
        retrofit.create(Service::class.java)
}