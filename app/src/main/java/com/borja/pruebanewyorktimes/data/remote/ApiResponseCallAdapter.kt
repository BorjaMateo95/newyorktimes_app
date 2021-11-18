package com.borja.pruebanewyorktimes.data.remote

import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

abstract class ApiResponseCallDelegate<TIn, TOut>(
    protected val proxy: Call<TIn>
) : Call<TOut> {
    override fun execute(): Response<TOut> = throw NotImplementedError()
    final override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)
    final override fun clone(): Call<TOut> = cloneImpl()

    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled

    abstract fun enqueueImpl(callback: Callback<TOut>)
    abstract fun cloneImpl(): Call<TOut>
    override fun timeout(): Timeout = Timeout().timeout(30, TimeUnit.SECONDS)
}

class ApiResponseCall<T>(proxy: Call<T>) :
    ApiResponseCallDelegate<T, ApiResponse<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<ApiResponse<T>>) =
        proxy.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, throwable: Throwable) {
                val result = if (throwable is IOException) {
                    ApiErrorResponse(throwable, throwable.localizedMessage)
                } else {
                    ApiErrorResponse(throwable, throwable.localizedMessage)
                }
                callback.onResponse(
                    this@ApiResponseCall,
                    Response.success(result)
                )
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback.onResponse(
                    this@ApiResponseCall,
                    Response.success(ApiResponse.success(response))
                )
            }

        })

    override fun cloneImpl(): Call<ApiResponse<T>> = ApiResponseCall(proxy.clone())

}

class ApiResponseCallAdapter(
    private val bodyType: Type
) : CallAdapter<Type, Call<ApiResponse<Type>>> {
    override fun adapt(original: Call<Type>): Call<ApiResponse<Type>> = ApiResponseCall(original)

    override fun responseType(): Type = bodyType
}

class ApiResponseCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                ApiResponse::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    ApiResponseCallAdapter(resultType)
                }
                else -> null
            }
        }
        else -> null
    }
}