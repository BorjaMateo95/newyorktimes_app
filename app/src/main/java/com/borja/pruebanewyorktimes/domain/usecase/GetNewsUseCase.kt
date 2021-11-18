package com.borja.pruebanewyorktimes.domain.usecase

import com.borja.pruebanewyorktimes.data.remote.dto.NewsResponse
import com.borja.pruebanewyorktimes.data.remote.repository.MostPopularPublicationsRepository
import com.borja.pruebanewyorktimes.domain.Either
import com.borja.pruebanewyorktimes.domain.Failure
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetNewsUseCase @Inject constructor(private val repository: MostPopularPublicationsRepository){
    suspend fun invoke(type: String, period: Int, mostPopularBy: String?) : Either<Failure, NewsResponse> =
        repository.getNews(type, period, mostPopularBy)

}