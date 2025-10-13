package com.example.juego_charadas

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Results", fontSize = 30.sp, fontWeight = FontWeight.Bold, fontFamily = wonderian)
            Spacer(modifier = Modifier.height(20.dp))
            //val validTeams = teamsList.filter { it.points >= 0 }

            //if (validTeams.isEmpty()) {
              //  Text("No hay resultados válidos 😅")
            //} else {
              //  validTeams.forEachIndexed { index, team ->
                //    Text("Team ${index + 1}: ${team.points} puntos")
                //}
            //}

        }
    }
}