package com.example.dogapp.model

import com.google.gson.annotations.SerializedName

data class Pet(
    @SerializedName("name")
    val name: String,
    @SerializedName("bread")
    val breed: String,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("symptoms")
    val symptoms: String,
)