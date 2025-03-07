package com.example.cs2_matches.model

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("id") val id: Int,
    @SerializedName("team") val team: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("rating") val rating: String
)