package com.borja.pruebanewyorktimes.data.remote.source

import com.borja.pruebanewyorktimes.data.remote.Service
import com.borja.pruebanewyorktimes.data.remote.dto.NewsFilterRequest
import com.borja.pruebanewyorktimes.data.remote.dto.NewsResponse
import com.borja.pruebanewyorktimes.domain.Either
import com.borja.pruebanewyorktimes.domain.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MostPopularPublicationsDataSource @Inject constructor(
    private val service: Service
) : RemoteDataSource {

    override suspend fun getNews(newsFilterRequest: NewsFilterRequest): Either<Failure, NewsResponse> =
        try {
            if (newsFilterRequest.mostSharedBy == null) {
                val response = service.getNews(newsFilterRequest.type, newsFilterRequest.period)
                Either.Right(response.body()!!)
            } else {
                val response = service.getNews(newsFilterRequest.type, newsFilterRequest.period,
                    newsFilterRequest.mostSharedBy)
                Either.Right(response.body()!!)
            }

        } catch (exception: Exception) {
            Either.Left(Failure.ApiFailure.GenericError(exception.message))
        }
}
