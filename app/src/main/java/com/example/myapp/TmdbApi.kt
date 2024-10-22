package com.example.myapp

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("trending/movie/week")
    suspend fun getMovieList(@Query("api_key") apiKey : String): MovieList

    @GET("movie/{id}?append_to_response=credits")
    suspend fun getMovieDetails(@Path("id") id: String, @Query("api_key") apiKey : String): Movie

    @GET("trending/tv/week")
    suspend fun getTvList(@Query("api_key") apiKey : String): Tv

    @GET("tv/{id}?append_to_response=credits")
    suspend fun getSerieDetails(@Path("id") id: String, @Query("api_key") apiKey : String): Serie

    @GET("trending/person/week")
    suspend fun getActorList(@Query("api_key") apiKey : String): ActorList

    @GET("person/{id}?append_to_response=credits")
    suspend fun getActorDetails(@Path("id") id: String, @Query("api_key") apiKey : String): Actor
}