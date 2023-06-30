package com.example.parcial2

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun getRandomJoke(@Url url: String): Response<ChuckNorrisRandomResponse>

   @GET
    suspend fun getCategories(@Url url: String): Response<ArrayList<String>>

    @GET
    suspend fun getByCategory(@Url url: String): Response<ChuckNorrisCategoryResponse>
}