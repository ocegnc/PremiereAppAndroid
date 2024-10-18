package com.example.myapp

import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("trending/movie/week")
    suspend fun getMovieList(@Query("api_key") apiKey : String): MovieList
}