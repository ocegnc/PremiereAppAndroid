package com.example.myapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel(): ViewModel() {

    val apikey = "317519a83cc36ab9367ba50e5aa75b40"

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(TmdbApi::class.java);

    val movies = MutableStateFlow<MovieList>(MovieList())
    val movie = MutableStateFlow<Movie>(Movie())
    val tv = MutableStateFlow<Tv>(Tv())
    val serie = MutableStateFlow<Serie>(Serie())
    val actors = MutableStateFlow<ActorList>(ActorList())
    val actor = MutableStateFlow<Actor>(Actor())

    fun getMovies(){
        viewModelScope.launch {
            movies.value = retrofit.getMovieList(apikey,"fr")
        }
    }

    fun getMovieDetails(id : String){
        viewModelScope.launch {
            movie.value = retrofit.getMovieDetails(id,apikey,"fr")
        }
    }

    fun getTv(){
        viewModelScope.launch {
            tv.value = retrofit.getTvList(apikey,"fr")
        }
    }

    fun getSerieDetails(id : String){
        viewModelScope.launch {
            serie.value = retrofit.getSerieDetails(id,apikey,"fr")
        }
    }

    fun getActors(){
        viewModelScope.launch {
            actors.value = retrofit.getActorList(apikey,"fr")
        }
    }

    fun getActorDetails(id : String){
        viewModelScope.launch {
            actor.value = retrofit.getActorDetails(id,apikey,"fr")
        }
    }

}