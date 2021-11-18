package com.borja.pruebanewyorktimes.domain

sealed class Failure {
    sealed class ApiFailure : Failure() {
        data class GenericError(val message: String) : ApiFailure()
    }

    object NetworkConnectionError : Failure()
    object ServerError : Failure()
    object UnauthorizedError : Failure()
    class ServerErrorWithException(val exception: Exception, val code: Int = 0) : Failure()
    abstract class FeatureFailure : Failure()

}