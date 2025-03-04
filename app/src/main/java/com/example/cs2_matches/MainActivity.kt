package com.example.cs2_matches

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
//import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.cs2_matches.ui.MatchDetailScreen
import com.example.cs2_matches.ui.MatchListScreen
import com.example.cs2_matches.ui.theme.CS2_MatchesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CS2_MatchesTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "matches") {
        composable("matches") { MatchListScreen(navController) }
        composable(
            "details/{matchId}",
            arguments = listOf(navArgument("matchId") { type = NavType.IntType })
        ) { backStackEntry ->
            val matchId = backStackEntry.arguments?.getInt("matchId") ?: 0
            MatchDetailScreen(matchId)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreview() {
    CS2_MatchesTheme {
        AppNavigation()
    }
}
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun MatchListScreenPreview() {
//    CS2_MatchesTheme {
//        MatchListScreen(navController = rememberNavController())
//    }
//}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun MatchDetailScreenPreview() {
//    CS2_MatchesTheme {
//        MatchDetailScreen(matchId = 123) // Use a fake match ID for preview
//    }
//}

