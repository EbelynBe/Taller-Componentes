package com.example.juego_charadas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.juego_charadas.model.Game
import com.example.juego_charadas.model.Team
import com.example.juego_charadas.ui.theme.buttonAnimation

class GameActivity : ComponentActivity() {

    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val category = intent.getStringExtra("category") ?: "Animals"
        val teamsList =
            intent.getSerializableExtra("teamsList") as? ArrayList<Team> ?: arrayListOf()

        game = Game(category, teamsList)

        setContent {
            GameScreen(game, onFinish = {
                val intent = Intent(this, ResultsActivity::class.java)
                intent.putExtra("category", category)
                startActivity(intent) // Keep activity open
            })
        }
    }

    @Composable
    fun GameScreen(game: Game, onFinish: () -> Unit) {
        val wonderian = FontFamily(Font(R.font.wonderian))

        val seconds by game.seconds
        val teamIndex by game.currentTeamIndex
        val word = game.selectedWord

        val colors = listOf(
            Color(0xFFFFCDD2), // Light red
            Color(0xFFC8E6C9), // Light green
            Color(0xFFBBDEFB), // Light blue
            Color(0xFFFFF9C4), // Light yellow
            Color(0xFFD1C4E9), // Light purple
            Color(0xFFFFE0B2)  // Light orange
        )

        var showNextTeamScreen by remember { mutableStateOf(false) }
        var backgroundColor by remember { mutableStateOf(colors.random()) }

        // Change background color when the word changes
        LaunchedEffect(word) {
            backgroundColor = colors.random()
        }

        // Start game timer
        LaunchedEffect(Unit) {
            game.startTimer { showNextTeamScreen = true }
        }

        // Navigate to results when the game finishes
        if (game.gameFinished.value) {
            LaunchedEffect(Unit) {
                onFinish()
            }
        }

        if (showNextTeamScreen) {
            // If all teams have played, go to results
            if (teamIndex + 1 >= game.teams.size) {
                LaunchedEffect(Unit) {
                    onFinish()
                }
            } else {
                // Next team screen
                NextTeamScreen(
                    teamNumber = (teamIndex + 1),
                    teamName = "Team ${(teamIndex + 2)}",
                    backgroundColor = colors[(teamIndex + 1) % colors.size],
                    onContinue = {
                        game.nextTeam()
                        game.resetTimer()
                        showNextTeamScreen = false
                        game.startTimer { showNextTeamScreen = true }
                    }
                )
            }
        } else {
            // Main game screen
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .padding(16.dp)
            ) {
                // Top-left info (timer, team, points)
                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 30.dp, start = 16.dp)
                ) {
                    Text(
                        text = "Time: $seconds s",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = wonderian,
                        fontSize = 22.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Team ${teamIndex + 1} turn",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = wonderian,
                        fontSize = 22.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Points: ${game.teams[teamIndex].points}",
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = wonderian,
                        fontSize = 22.sp
                    )
                }

                // Control buttons (pause / restart)
                Column(
                    modifier = Modifier.align(Alignment.TopEnd),
                    horizontalAlignment = Alignment.End
                ) {
                    Button(onClick = { game.stopTimer() }) {
                        Text("Pause")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        game.resetTimer()
                        game.startTimer { showNextTeamScreen = true }
                    }) {
                        Text("Restart")
                    }
                }

                // Word and correct / wrong buttons
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(horizontal = 30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ImagenBoton(
                        drawableId = R.drawable.wrong,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(70.dp)
                    ) {
                        game.nextWord(0)
                    }

                    Text(
                        text = word,
                        fontFamily = wonderian,
                        fontSize = 100.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    ImagenBoton(
                        drawableId = R.drawable.correct,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(70.dp)
                    ) {
                        game.nextWord(1)
                    }
                }

                // Return to home button
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    ImagenBoton(
                        drawableId = R.drawable.incio,
                        modifier = Modifier.size(80.dp)
                    ) {
                        finish()
                    }
                }
            }
        }
    }

    @Composable
    fun NextTeamScreen(
        teamNumber: Int,
        teamName: String,
        backgroundColor: Color,
        onContinue: () -> Unit
    ) {
        val wonderian = FontFamily(Font(R.font.wonderian))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Next Turn:",
                    fontFamily = wonderian,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = teamName,
                    fontFamily = wonderian,
                    fontSize = 60.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = onContinue) {
                    Text("Continue")
                }
            }
        }
    }

    @Composable
    fun ImagenBoton(
        drawableId: Int,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        buttonAnimation(
            drawableId = drawableId,
            onClick = onClick,
            modifier = modifier
        )
    }
}
