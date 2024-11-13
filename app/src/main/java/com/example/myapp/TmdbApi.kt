package com.example.myapp

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("search/collection")
    suspend fun getCollection(@Query("api_key") apiKey: String, @Query("language")language : String, @Query("keyword") keyword: String): Collection

    @GET("trending/movie/week")
    suspend fun getMovieList(@Query("api_key") apiKey : String, @Query("language")language : String): MovieList

    @GET("movie/{id}?append_to_response=credits")
    suspend fun getMovieDetails(@Path("id") id: String, @Query("api_key") apiKey : String, @Query("language")language : String): Movie

    @GET("trending/tv/week")
    suspend fun getTvList(@Query("api_key") apiKey : String,@Query("language")language : String): Tv

    @GET("tv/{id}?append_to_response=credits")
    suspend fun getSerieDetails(@Path("id") id: String, @Query("api_key") apiKey : String,@Query("language")language : String): Serie

    @GET("trending/person/week")
    suspend fun getActorList(@Query("api_key") apiKey : String,@Query("language")language : String): ActorList

    @GET("person/{id}?append_to_response=credits")
    suspend fun getActorDetails(@Path("id") id: String, @Query("api_key") apiKey : String,@Query("language")language : String): Actor

    @GET("search/movie")
    suspend fun searchMovie(@Query("api_key") apikey : String, @Query("query") query: String): MovieList

    @GET("search/tv")
    suspend fun searchSerie(@Query("api_key") apikey : String, @Query("query") query: String): Tv

    @GET("search/person")
    suspend fun searchActor(@Query("api_key") apikey : String, @Query("query") query: String): ActorList
}