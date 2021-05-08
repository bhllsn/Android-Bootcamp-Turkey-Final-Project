package com.bhllsn.expensetrackerplus.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val API_URL = "https://v6.exchangerate-api.com/v6/58ecc36ca5e14bc44d8231a8/latest/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(API_URL).build()

interface ApiService
{
    @GET("EUR")
    fun getExchangeRates() : Call<ApiResponse>
}

object Api { val retrofitService : ApiService by lazy { retrofit.create(ApiService::class.java) } }

//API KEY : 58ecc36ca5e14bc44d8231a8