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


//    private fun fetchMatches() {
//        viewModelScope.launch {
//            val api = MatchesApi.create()
//            try {
//                val response = api.getMatches()
//
//                // Log API response
//                Log.d("API_RESPONSE", "Fetched ${response.size} matches: $response")
//
//                uiState.value = MatchUiState.Success(response)
//            } catch (e: Exception) {
//                Log.e("API_ERROR", "Failed to fetch matches: ${e.message}")
//
//                uiState.value = MatchUiState.Error
//            }
//        }
//    }
private fun fetchMatches() {
    viewModelScope.launch {
        try {
            val testMatches = listOf(
                Match(
                    id = 1,
                    time = "2024-03-05T12:00:00.000Z",
                    event = Event("Test Event", "https://img-cdn.hltv.org/eventlogo/nYADQoBBHeOXRjBW1kFOra.png?ixlib=java-2.1.0&w=50&s=cdec2e890642716f603d275d582433eb"),
                    stars = 2,
                    maps = "BO3",
                    teams = listOf(
                        Team(1, "Mouz", "https://yt3.googleusercontent.com/iRY7jxVRvL3yDAgetgwroorXHCbEmaid_NFnwYVf9tpQVncwtNo5IyZvbprCKtg-iBKyBojT=s900-c-k-c0x00ffffff-no-rj"),
                        Team(2, "Outsiders", "https://img-cdn.hltv.org/teamlogo/K15IzjKuVPxnoWr3J3-tJ7.png?ixlib=java-2.1.0&w=50&s=d87bd1cf1f3835f152b363eecd95e3fe")
                    )
                ),
                Match(
                    id = 2,
                    time = "2024-03-05T12:00:00.000Z",
                    event = Event("Test Event", "https://img-cdn.hltv.org/eventlogo/nYADQoBBHeOXRjBW1kFOra.png?ixlib=java-2.1.0&w=50&s=cdec2e890642716f603d275d582433eb"),
                    stars = 2,
                    maps = "BO3",
                    teams = listOf(
                        Team(1, "Mouz", "https://yt3.googleusercontent.com/iRY7jxVRvL3yDAgetgwroorXHCbEmaid_NFnwYVf9tpQVncwtNo5IyZvbprCKtg-iBKyBojT=s900-c-k-c0x00ffffff-no-rj"),
                        Team(2, "Outsiders", "https://img-cdn.hltv.org/teamlogo/K15IzjKuVPxnoWr3J3-tJ7.png?ixlib=java-2.1.0&w=50&s=d87bd1cf1f3835f152b363eecd95e3fe")
                    )
                ),
                Match(
                    id = 3,
                    time = "2024-03-05T12:00:00.000Z",
                    event = Event("Test Event", "https://img-cdn.hltv.org/eventlogo/nYADQoBBHeOXRjBW1kFOra.png?ixlib=java-2.1.0&w=50&s=cdec2e890642716f603d275d582433eb"),
                    stars = 2,
                    maps = "BO3",
                    teams = listOf(
                        Team(1, "Mouz", "https://yt3.googleusercontent.com/iRY7jxVRvL3yDAgetgwroorXHCbEmaid_NFnwYVf9tpQVncwtNo5IyZvbprCKtg-iBKyBojT=s900-c-k-c0x00ffffff-no-rj"),
                        Team(2, "Outsiders", "https://img-cdn.hltv.org/teamlogo/K15IzjKuVPxnoWr3J3-tJ7.png?ixlib=java-2.1.0&w=50&s=d87bd1cf1f3835f152b363eecd95e3fe")
                    )
                ),
                Match(
                    id = 4,
                    time = "2024-03-05T12:00:00.000Z",
                    event = Event("Test Event", "https://img-cdn.hltv.org/eventlogo/nYADQoBBHeOXRjBW1kFOra.png?ixlib=java-2.1.0&w=50&s=cdec2e890642716f603d275d582433eb"),
                    stars = 2,
                    maps = "BO3",
                    teams = listOf(
                        Team(1, "Mouz", "https://yt3.googleusercontent.com/iRY7jxVRvL3yDAgetgwroorXHCbEmaid_NFnwYVf9tpQVncwtNo5IyZvbprCKtg-iBKyBojT=s900-c-k-c0x00ffffff-no-rj"),
                        Team(2, "Outsiders", "https://img-cdn.hltv.org/teamlogo/K15IzjKuVPxnoWr3J3-tJ7.png?ixlib=java-2.1.0&w=50&s=d87bd1cf1f3835f152b363eecd95e3fe")
                    )
                ),
                Match(
                    id = 5,
                    time = "2024-03-05T12:00:00.000Z",
                    event = Event("Test Event", "https://img-cdn.hltv.org/eventlogo/nYADQoBBHeOXRjBW1kFOra.png?ixlib=java-2.1.0&w=50&s=cdec2e890642716f603d275d582433eb"),
                    stars = 2,
                    maps = "BO3",
                    teams = listOf(
                        Team(1, "Mouz", "https://yt3.googleusercontent.com/iRY7jxVRvL3yDAgetgwroorXHCbEmaid_NFnwYVf9tpQVncwtNo5IyZvbprCKtg-iBKyBojT=s900-c-k-c0x00ffffff-no-rj"),
                        Team(2, "Outsiders", "https://img-cdn.hltv.org/teamlogo/K15IzjKuVPxnoWr3J3-tJ7.png?ixlib=java-2.1.0&w=50&s=d87bd1cf1f3835f152b363eecd95e3fe")
                    )
                ),
                Match(
                    id = 6,
                    time = "2024-03-05T12:00:00.000Z",
                    event = Event("Test Event", "https://img-cdn.hltv.org/eventlogo/nYADQoBBHeOXRjBW1kFOra.png?ixlib=java-2.1.0&w=50&s=cdec2e890642716f603d275d582433eb"),
                    stars = 2,
                    maps = "BO3",
                    teams = listOf(
                        Team(1, "Mouz", "https://yt3.googleusercontent.com/iRY7jxVRvL3yDAgetgwroorXHCbEmaid_NFnwYVf9tpQVncwtNo5IyZvbprCKtg-iBKyBojT=s900-c-k-c0x00ffffff-no-rj"),
                        Team(2, "Outsiders", "https://img-cdn.hltv.org/teamlogo/K15IzjKuVPxnoWr3J3-tJ7.png?ixlib=java-2.1.0&w=50&s=d87bd1cf1f3835f152b363eecd95e3fe")
                    )
                ),
                Match(
                    id = 7,
                    time = "2024-03-05T12:00:00.000Z",
                    event = Event("Test Event", "https://img-cdn.hltv.org/eventlogo/nYADQoBBHeOXRjBW1kFOra.png?ixlib=java-2.1.0&w=50&s=cdec2e890642716f603d275d582433eb"),
                    stars = 2,
                    maps = "BO3",
                    teams = listOf(
                        Team(1, "Mouz", "https://yt3.googleusercontent.com/iRY7jxVRvL3yDAgetgwroorXHCbEmaid_NFnwYVf9tpQVncwtNo5IyZvbprCKtg-iBKyBojT=s900-c-k-c0x00ffffff-no-rj"),
                        Team(2, "Outsiders", "https://img-cdn.hltv.org/teamlogo/K15IzjKuVPxnoWr3J3-tJ7.png?ixlib=java-2.1.0&w=50&s=d87bd1cf1f3835f152b363eecd95e3fe")
                    )
                ),
                Match(
                    id = 8,
                    time = "2024-03-05T12:00:00.000Z",
                    event = Event("Test Event", "https://img-cdn.hltv.org/eventlogo/nYADQoBBHeOXRjBW1kFOra.png?ixlib=java-2.1.0&w=50&s=cdec2e890642716f603d275d582433eb"),
                    stars = 2,
                    maps = "BO3",
                    teams = listOf(
                        Team(1, "Mouz", "https://yt3.googleusercontent.com/iRY7jxVRvL3yDAgetgwroorXHCbEmaid_NFnwYVf9tpQVncwtNo5IyZvbprCKtg-iBKyBojT=s900-c-k-c0x00ffffff-no-rj"),
                        Team(2, "Outsiders", "https://img-cdn.hltv.org/teamlogo/K15IzjKuVPxnoWr3J3-tJ7.png?ixlib=java-2.1.0&w=50&s=d87bd1cf1f3835f152b363eecd95e3fe")
                    )
                )
            )
            Log.d("API_RESPONSE", "Using test data")

            uiState.value = MatchUiState.Success(testMatches)
        } catch (e: Exception) {
            uiState.value = MatchUiState.Error
        }
    }
}

}