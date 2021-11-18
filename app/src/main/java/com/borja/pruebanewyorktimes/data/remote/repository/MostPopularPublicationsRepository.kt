package com.borja.pruebanewyorktimes.data.remote.repository

import com.borja.pruebanewyorktimes.data.remote.dto.NewsFilterRequest
import com.borja.pruebanewyorktimes.data.remote.dto.NewsResponse
import com.borja.pruebanewyorktimes.data.remote.source.MostPopularPublicationsDataSource
import com.borja.pruebanewyorktimes.domain.Either
import com.borja.pruebanewyorktimes.domain.Failure
import javax.inject.Inject

class MostPopularPublicationsRepository @Inject constructor(
    private val remoteDataSource: MostPopularPublicationsDataSource
) {
    suspend fun getNews(type: String, period: Int, mostPopularBy: String?) : Either<Failure, NewsResponse> {
        return remoteDataSource.getNews(NewsFilterRequest(type, period, mostPopularBy))
    }
}