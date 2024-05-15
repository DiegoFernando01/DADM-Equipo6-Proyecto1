package com.example.dogapp.api

import retrofit2.Call
import retrofit2.http.GET
import com.example.dogapp.model.DogBreedsResponse

interface DogApiService {
    @GET("breeds/list/all")
    fun getAllBreeds(): Call<DogBreedsResponse>
}