package com.example.cs2_matches.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.example.cs2_matches.model.Match
import com.example.cs2_matches.model.Player
import com.example.cs2_matches.network.MatchesApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchDetailScreen(matchId: Int, navController: NavController) {
    val api = remember { MatchesApi.create() }
    val scope = rememberCoroutineScope()

    var match by remember { mutableStateOf<Match?>(null) }
    var players by remember { mutableStateOf<List<Player>>(emptyList()) }

    // Fetch match details and players
    LaunchedEffect(matchId) {
        scope.launch {
            try {
                val matches = api.getMatches()
                match = matches.find { it.id == matchId }

                match?.let { selectedMatch ->
                    val allPlayers = api.getPlayers()
                    players = selectedMatch.teams.flatMap { team ->
                        allPlayers.filter { it.team == team.name }.take(5) // Limit to 5 players per team
                    }
                }
            } catch (e: Exception) {
                match = null
                players = emptyList()
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFF5b698c),
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Match", color = Color.White)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1D2730))
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF1D2730),
                contentColor = Color.White
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Test")
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            match?.let { selectedMatch ->
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    // Match Title: "Team A vs Team B"
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${selectedMatch.teams.getOrNull(0)?.name ?: "Unknown"} vs ${
                                selectedMatch.teams.getOrNull(
                                    1
                                )?.name ?: "Unknown"
                            }",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Lineups Section
                    Text(text = "Team Lineups:", style = MaterialTheme.typography.titleMedium)

                    Spacer(modifier = Modifier.height(8.dp))

                    // Splitting teams into two lists
                    val team1 = players.filter { it.team == selectedMatch.teams.getOrNull(0)?.name }
                    val team2 = players.filter { it.team == selectedMatch.teams.getOrNull(1)?.name }

                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(team1.zip(team2)) { (player1, player2) ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Left Team Player
                                Text(text = "${player1.nickname} (${player1.rating})", modifier = Modifier.weight(1f))

                                // Right Team Player
                                Text(
                                    text = "${player2.nickname} (${player2.rating})",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                    }
                }
            } ?: Text(text = "Loading match details...", color = Color.White)
        }
    }
}
