package com.example.juego_charadas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.juego_charadas.model.Team
import com.example.juego_charadas.ui.theme.buttonAnimation

class TeamsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val numTeams = intent.getIntExtra("teams", 2)
        val totalPlayers = intent.getIntExtra("totalPlayers", 2)
        val category = intent.getStringExtra("category") ?: "No category"

        val customFont = FontFamily(Font(R.font.wonderian))

        // Check if a list of teams comes from another activity (such as Results)
        val receivedTeams = intent.getSerializableExtra("teamsList") as? ArrayList<Team>

        // If teams come, use them. If not, create new ones.
        val baseTeams = if (!receivedTeams.isNullOrEmpty()) {
            receivedTeams
        } else {
            val playersDistribution = distributePlayersAcrossTeams(totalPlayers, numTeams)
            ArrayList(playersDistribution.map { count -> Team(players = count, points = 0) })
        }

        setContent {
            TeamsSelector(
                baseTeams = baseTeams,
                categoryName = category,
                customFont = customFont,
                onBack = {
                    val intent = Intent(this, MainActivity::class.java)
                    finish() },
                onStartGame = {
                    val intent = Intent(this, CountdownActivity::class.java)
                    intent.putExtra("teams", numTeams)
                    intent.putExtra("category", category)
                    intent.putExtra("teamsList", ArrayList(baseTeams))
                    startActivity(intent)
                    finish()
                }
            )
        }
    }

    // Distribute players equally between teams
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
    baseTeams: List<Team>,
    categoryName: String,
    customFont: FontFamily,
    onBack: () -> Unit,
    onStartGame: () -> Unit
) {
    val teamsList = remember { mutableStateListOf<Team>().apply { addAll(baseTeams) } }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
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
            // Header with back button and category name
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                buttonAnimation(
                    drawableId = R.drawable.back_arrow,
                    onClick = onBack,
                    modifier = Modifier.size(45.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Category: $categoryName",
                    fontSize = 32.sp,
                    fontFamily = customFont,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Grid showing teams info
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
                        // Team card with animation
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

            Spacer(modifier = Modifier.height(20.dp))

            // Blue button to continue
            Button(
                onClick = onStartGame,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .width(200.dp)
                    .height(60.dp)
            ) {
                Text(
                    text = "Continue",
                    fontSize = 28.sp,
                    fontFamily = customFont,
                    color = Color.White
                )
            }
        }
    }
}
