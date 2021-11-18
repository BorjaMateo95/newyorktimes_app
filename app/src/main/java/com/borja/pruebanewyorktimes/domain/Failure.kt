package com.borja.pruebanewyorktimes.domain

sealed class Failure {
    sealed class ApiFailure : Failure() {
        data class GenericError(val message: String?) : ApiFailure()
    }
}