package com.example.cs2_matches.network

import com.example.cs2_matches.model.Match
import com.example.cs2_matches.model.Player
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MatchesApi {
    @GET("matchesByEvent.json")
    suspend fun getMatches(): List<Match>

    @GET("match.json")
    suspend fun getPlayers(): List<Player> // New API call for fetching players



    companion object {
        private const val BASE_URL = "https://hltv-api.vercel.app/api/"

        fun create(): MatchesApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MatchesApi::class.java)
        }
    }
}