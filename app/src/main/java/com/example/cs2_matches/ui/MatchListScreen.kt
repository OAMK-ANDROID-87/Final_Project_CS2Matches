package com.example.cs2_matches.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cs2_matches.R
import com.example.cs2_matches.model.Match
import com.example.cs2_matches.viewmodel.MatchUiState
import com.example.cs2_matches.viewmodel.MatchesViewModel
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchListScreen(navController: NavController, viewModel: MatchesViewModel = viewModel()) {
    val uiState by viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(text = stringResource(id = R.string.match_list_title))
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Add info modal */ }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info",
                            modifier = Modifier.clickable { /* Handle click */ }
                        )

                    }
                }
            )
        }
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
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = rememberAsyncImagePainter(model = match.event.logo),
                        contentDescription = "Event Logo",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${match.event.name} | ${match.maps}",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = rememberAsyncImagePainter(model = match.teams.getOrNull(0)?.logo),
                        contentDescription = "Team 1 Logo",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = match.teams.getOrNull(0)?.name ?: "Unknown", style = MaterialTheme.typography.titleLarge)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = rememberAsyncImagePainter(model = match.teams.getOrNull(1)?.logo),
                        contentDescription = "Team 2 Logo",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = match.teams.getOrNull(1)?.name ?: "Unknown", style = MaterialTheme.typography.titleLarge)
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Spacer(modifier = Modifier.height(4.dp))

                Text(text = match.time.split("T")[1].substring(0, 5), style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}
