package com.example.juego_charadas

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.juego_charadas.model.Team
import com.example.juego_charadas.ui.theme.buttonAnimation

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Get data from intent
            val teamsList = intent.getSerializableExtra("teamsList") as? ArrayList<Team> ?: arrayListOf()
            val category = intent.getStringExtra("category") ?: "Animals"
            val numTeams = teamsList.size
            val result = intent.getIntExtra("result", 0)

            // Load result screen
            ResultScreen(result,
                onTeamsResults = {
                    // Go back to TeamsActivity with data
                    val intent = Intent(this, TeamsActivity::class.java)
                    intent.putExtra("teams", numTeams)
                    intent.putExtra("category", category)
                    intent.putExtra("teamsList", ArrayList(teamsList))
                    intent.putExtra("fromResults", true) // Flag to indicate coming from results
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}

@Composable
fun ResultScreen(result: Int, onTeamsResults: () -> Unit) {
    val wonderian = FontFamily(Font(R.font.wonderian))

    // Colors for team gradients
    val teamColors = listOf(
        Color(0xFFFF9800), // Orange - Team 1
        Color(0xFF2196F3), // Blue - Team 2
        Color(0xFF9C27B0), // Purple - Team 3
        Color(0xFF4CAF50)  // Green - Team 4
    )

    // Winning team number
    val winnerTeam = result

    // Background color gradient
    val backgroundBrush = if (winnerTeam == 0) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF9E9E9E),
                Color.Black
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                teamColors[winnerTeam - 1],
                Color.Black
            )
        )
    }

    // Main content box
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Results",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = wonderian,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Show tie or winner
            if (winnerTeam == 0) {
                Text(
                    text = "🤝It's a tie!🤝",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = wonderian,
                    color = Color.White
                )
            } else {
                Text(
                    text = "🏆 Team $winnerTeam Winner! 🏆",
                    fontSize = 60.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = wonderian,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    // Start button to go back
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        buttonAnimation(
            drawableId = R.drawable.incio,
            onClick = onTeamsResults,
            modifier = Modifier.size(90.dp)
        )
    }
}
