package com.example.cs2_matches.model

import com.google.gson.annotations.SerializedName

data class Match(
    @SerializedName("id") val id: Int,
    @SerializedName("time") val time: String,
    @SerializedName("event") val event: Event,
    @SerializedName("stars") val stars: Int,
    @SerializedName("maps") val maps: String,
    @SerializedName("teams") val teams: List<Team>
)

data class Event(
    @SerializedName("name") val name: String,
    @SerializedName("logo") val logo: String
)

data class Team(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("logo") val logo: String
)
