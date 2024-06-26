package com.example.dogapp.webservice

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.dogapp.DogBreedsResponse
import com.example.dogapp.ImageResponse
import com.example.dogapp.utils.Constants.END_POINT

interface DogApiService {
    @GET(END_POINT)
    fun getAllBreeds(): Call<DogBreedsResponse>

    @GET("breed/{breed}/images/random")
    fun getBreedImage(@Path("breed") breed: String): Call<ImageResponse>
}

