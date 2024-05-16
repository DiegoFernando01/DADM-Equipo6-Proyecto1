package com.example.dogapp

data class DogBreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)

data class ImageResponse(
    val message: String,
    val status: String
)
