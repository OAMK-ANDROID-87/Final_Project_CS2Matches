package com.example.cs2_matches.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.cs2_matches.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cs2_matches.viewmodel.MatchUiState
import com.example.cs2_matches.viewmodel.MatchesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchDetailScreen(matchId: Int, viewModel: MatchesViewModel = viewModel()) {
    val uiState by viewModel.uiState

    val match = (uiState as? MatchUiState.Success)?.matches?.find { it.id == matchId }

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(id = R.string.match_details_title)) }) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            match?.let {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "${it.teams.getOrNull(0)?.name ?: "Unknown"} vs ${it.teams.getOrNull(1)?.name ?: "Unknown"}",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(text = stringResource(id = R.string.event_text, it.event.name))
                    Text(text = stringResource(id = R.string.date_text, it.time))
                    Text(text = stringResource(id = R.string.format_text, it.maps))

                }
            } ?: Text("Match not found")
        }
    }
}
