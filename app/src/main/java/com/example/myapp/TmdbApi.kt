package com.example.myapp

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("trending/movie/week")
    suspend fun getMovieList(@Query("api_key") apiKey : String): MovieList

    @GET("movie/{id}?append_to_response=credits")
    suspend fun getMovieDetails(@Path("id") id: String, @Query("api_key") apiKey : String): Movie

}