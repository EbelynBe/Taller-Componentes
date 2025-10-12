package com.example.juego_charadas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import com.example.juego_charadas.model.Team
import com.example.juego_charadas.ui.theme.buttonAnimation

class TeamsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val numTeams = intent.getIntExtra("teams", 2)
        val totalPlayers = intent.getIntExtra("totalPlayers", 2)
        val category = intent.getStringExtra("category") ?: "Sin categoría"

        // 🧮 Distribuir jugadores equitativamente entre equipos
        val playersDistribution = distributePlayersAcrossTeams(totalPlayers, numTeams)
        val teamsList = playersDistribution.map { count -> Team(players = count, points = 0) }

        val customFont = FontFamily(Font(R.font.wonderian))

        setContent {
            TeamsSelector(
                teamsList = teamsList,
                categoryName = category,
                customFont = customFont,
                onBack = { finish() },
                onStartGame = {
                    val intent = Intent(this, CountdownActivity::class.java)
                    intent.putExtra("teams", numTeams)
                    intent.putExtra("category", category)
                    startActivity(intent)
                }
            )
        }
    }

    private fun distributePlayersAcrossTeams(totalPlayers: Int, numTeams: Int): List<Int> {
        val base = totalPlayers / numTeams
        val remainder = totalPlayers % numTeams
        return List(numTeams) { index ->
            if (index < remainder) base + 1 else base
        }
    }
}

@Composable
fun TeamsSelector(
    teamsList: List<Team>,
    categoryName: String,
    customFont: FontFamily,
    onBack: () -> Unit,
    onStartGame: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Category: $categoryName",
                fontSize = 32.sp,
                fontFamily = customFont,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(30.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                itemsIndexed(teamsList) { index, team ->
                    val drawableId = when (index) {
                        0 -> R.drawable.background_orange
                        1 -> R.drawable.background_blue
                        2 -> R.drawable.background_purple
                        3 -> R.drawable.background_green
                        4 -> R.drawable.background_red
                        else -> R.drawable.background_orange
                    }

                    Box(
                        modifier = Modifier
                            .width(180.dp)
                            .height(160.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        buttonAnimation(
                            drawableId = drawableId,
                            modifier = Modifier.fillMaxSize(),
                            onClick = {}
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Team ${index + 1}",
                                fontSize = 28.sp,
                                fontFamily = customFont,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Players: ${team.players}",
                                fontSize = 20.sp,
                                fontFamily = customFont,
                                color = Color.White
                            )
                            Text(
                                text = "Points: ${team.points}",
                                fontSize = 20.sp,
                                fontFamily = customFont,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        // 🔙 Botón atrás
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            buttonAnimation(
                drawableId = R.drawable.backbutton,
                onClick = onBack,
                modifier = Modifier.size(90.dp)
            )
        }

        // ▶️ Botón iniciar
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            buttonAnimation(
                drawableId = R.drawable.incio,
                onClick = onStartGame,
                modifier = Modifier.size(90.dp)
            )
        }
    }
}
