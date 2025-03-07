package com.example.cs2_matches.ui

import androidx.compose.foundation.background
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
import coil.compose.AsyncImage
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
                Column(modifier = Modifier.fillMaxSize()) {
                    // Match Header
                    MatchHeader(selectedMatch)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Lineups Section
                    Text(text = "Team Lineups:", style = MaterialTheme.typography.titleMedium)

                    Spacer(modifier = Modifier.height(8.dp))

                    // Splitting teams into two lists
                    val team1 = players.filter { it.team == selectedMatch.teams.getOrNull(0)?.name }
                    val team2 = players.filter { it.team == selectedMatch.teams.getOrNull(1)?.name }

                    LazyColumn(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
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

@Composable
fun MatchHeader(selectedMatch: Match) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tournament Title (Centered)
        Text(
            text = selectedMatch.event.name,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Match Info Card
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f) // Adjust size
                .background(Color(0xFF9BA9BB), MaterialTheme.shapes.medium)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Teams and Logos Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Team 1
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = selectedMatch.teams.getOrNull(0)?.logo,
                            contentDescription = "Team 1 Logo",
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = selectedMatch.teams.getOrNull(0)?.name ?: "Unknown",
                            color = Color.White,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    // Match Time
                    Text(
                        text = selectedMatch.time.split("T")[1].substring(0, 5),
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    // Team 2
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = selectedMatch.teams.getOrNull(1)?.logo,
                            contentDescription = "Team 2 Logo",
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = selectedMatch.teams.getOrNull(1)?.name ?: "Unknown",
                            color = Color.White,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}

