package com.borja.pruebanewyorktimes.data.remote.source

import com.borja.pruebanewyorktimes.data.remote.dto.NewsFilterRequest
import com.borja.pruebanewyorktimes.data.remote.dto.NewsResponse
import com.borja.pruebanewyorktimes.domain.Either
import com.borja.pruebanewyorktimes.domain.Failure

interface RemoteDataSource {
    suspend fun getNews(newsFilterRequest: NewsFilterRequest): Either<Failure, NewsResponse>
}