package com.example.juego_charadas

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

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //val teamsList = intent.getSerializableExtra("teamsList") as? ArrayList<Team> ?: arrayListOf()
            //ResultScreen(teamsList)
            ResultScreen()
        }
    }
}

@Composable //teamsList: ArrayList<Team>
fun ResultScreen() {
    val wonderian = FontFamily(Font(R.font.wonderian))

    // 🎨 Degradado con colores de equipos (simulados)
    val teamColors = listOf(
        Color(0xFFFF9800), // 🟧 Team 1
        Color(0xFF2196F3), // 🟦 Team 2
        Color(0xFF9C27B0), // 🟪 Team 3
        Color(0xFF4CAF50)  // 🟩 Team 4
    )

    // 🏆 Simulación: Team 1 ganó
    val winnerTeam = 1

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            teamColors[winnerTeam - 1],
            Color.Black
        )
    )

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
            Text(
                "Results",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = wonderian,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "🏆 Team $winnerTeam Winner! 🏆",
                fontSize = 60.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = wonderian,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Comentarios del código original (no se tocan)
            //val validTeams = teamsList.filter { it.points >= 0 }

            //if (validTeams.isEmpty()) {
            //    Text("No hay resultados válidos 😅")
            //} else {
            //    validTeams.forEachIndexed { index, team ->
            //        Text("Team ${index + 1}: ${team.points} puntos")
            //    }
            //}
        }
    }
}
