package com.example.cs2_matches.viewmodel

import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cs2_matches.model.Match
import com.example.cs2_matches.network.MatchesApi
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.cs2_matches.model.Event
import com.example.cs2_matches.model.Team


sealed interface MatchUiState {
    data class Success(val matches: List<Match>) : MatchUiState
    object Loading : MatchUiState
    object Error : MatchUiState
}

@OptIn(UnstableApi::class)
class MatchesViewModel : ViewModel() {
    var uiState = mutableStateOf<MatchUiState>(MatchUiState.Loading)
        private set

    init {
        fetchMatches()
    }


    fun fetchMatches() {
        viewModelScope.launch {
            val api = MatchesApi.create()
            try {
                val response = api.getMatches()

                // Log API response
                Log.d("API_RESPONSE", "Fetched ${response.size} matches: $response")

                uiState.value = MatchUiState.Success(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Failed to fetch matches: ${e.message}")

                uiState.value = MatchUiState.Error
            }
        }
    }
    fun fetchEventMatches() {
        viewModelScope.launch {
            val api = MatchesApi.create()
            try {
                val response = api.getMatchesByEvent()

                // Log API response
                Log.d("API_RESPONSE", "Fetched ${response.size} matches: $response")

                uiState.value = MatchUiState.Success(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Failed to fetch matches: ${e.message}")

                uiState.value = MatchUiState.Error
            }
        }
    }

}