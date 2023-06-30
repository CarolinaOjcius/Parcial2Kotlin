package com.example.parcial2

import com.google.gson.annotations.SerializedName

data class ChuckNorrisRandomResponse(
    val categories: List<String>,
    val id: String,
    @SerializedName("value")val joke: String
)
