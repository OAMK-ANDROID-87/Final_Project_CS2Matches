package com.example.cs2_matches.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cs2_matches.model.Match
import com.example.cs2_matches.viewmodel.MatchUiState
import com.example.cs2_matches.viewmodel.MatchesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchListScreen(navController: NavController, viewModel: MatchesViewModel = viewModel()) {
    val uiState by viewModel.uiState

    Scaffold(
        topBar = { TopAppBar(title = { Text("CS2 Matches") }) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (uiState) {
                is MatchUiState.Loading -> Text("Loading...")
                is MatchUiState.Success -> MatchList(
                    matches = (uiState as MatchUiState.Success).matches,
                    navController = navController
                )
                is MatchUiState.Error -> Text("Failed to load matches")
            }
        }
    }
}

@Composable
fun MatchList(matches: List<Match>, navController: NavController) {
    LazyColumn {
        items(matches) { match ->
            MatchItem(match) { navController.navigate("details/${match.id}") }
        }
    }
}

@Composable
fun MatchItem(match: Match, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${match.teams.getOrNull(0)?.name ?: "Unknown"} vs ${match.teams.getOrNull(1)?.name ?: "Unknown"}",
                style = MaterialTheme.typography.titleLarge)
            Text(text = match.event.name, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Format: ${match.maps}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

