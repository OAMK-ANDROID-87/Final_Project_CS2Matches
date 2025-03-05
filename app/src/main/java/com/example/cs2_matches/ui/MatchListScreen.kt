package com.example.cs2_matches.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.cs2_matches.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
        topBar = { TopAppBar(title = { Text(stringResource(id = R.string.match_list_title)) }) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (uiState) {
                is MatchUiState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))


                is MatchUiState.Success -> MatchList(
                    matches = (uiState as MatchUiState.Success).matches,
                    navController = navController
                )
                is MatchUiState.Error -> Text(stringResource(id = R.string.error_text))
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

