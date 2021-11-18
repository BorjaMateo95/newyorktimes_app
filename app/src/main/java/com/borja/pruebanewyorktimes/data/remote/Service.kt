package com.borja.pruebanewyorktimes.data.remote

import com.borja.pruebanewyorktimes.data.remote.dto.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {

    @GET("{type}/all-sections/{mostPopularBy}{period}.json?api-key=2bB0BGGe2pysadVQorASzqyClBzI5w1G")
    suspend fun getNews(@Path("type") type: String,
                        @Path("period") period: Int,
                        @Path("mostPopularBy", encoded = true) mostPopularBy: String?): Response<NewsResponse>

    @GET("{type}/all-sections/{period}.json?api-key=2bB0BGGe2pysadVQorASzqyClBzI5w1G")
    suspend fun getNews(@Path("type") type: String,
                        @Path("period") period: Int): Response<NewsResponse>
}