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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
    var showInfoDialog by remember { mutableStateOf(false) }
    var showEventMatches by remember { mutableStateOf(false) }

    LaunchedEffect(showEventMatches) {
        if (showEventMatches) {
            viewModel.fetchEventMatches()
        } else {
            viewModel.fetchMatches()
        }
    }

    Scaffold(
        containerColor= Color(0xFF5b698c),
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(id = R.string.match_list_title),
                            color = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showInfoDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1D2730)
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF1D2730),
                contentColor = Color.White
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { showEventMatches = !showEventMatches },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF292B3A))
                ) {
                    Text(
                        text = if (showEventMatches) "Show Regular Matches" else "Show Big Events Matches",
                        color = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (uiState) {
                is MatchUiState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                is MatchUiState.Success -> {
                    val uMatches = (uiState as MatchUiState.Success).matches

                    MatchList(matches = uMatches, navController = navController)
                }
                is MatchUiState.Error -> Text(stringResource(id = R.string.error_text))
            }
        }
    }

    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            text = {
                Text(
                    text = stringResource(id = R.string.about_text),
                    textAlign = TextAlign.Center
                )
            },
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "About", textAlign = TextAlign.Center)
                }
            },
            confirmButton = {
                TextButton(onClick = { showInfoDialog = false }) {
                    Text("OK")
                }
            }
        )
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
            .clickable { onClick() },
            colors = CardDefaults.cardColors(containerColor = Color(0xFF9BA9BB)),
            elevation = CardDefaults.elevatedCardElevation(4.dp)
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
