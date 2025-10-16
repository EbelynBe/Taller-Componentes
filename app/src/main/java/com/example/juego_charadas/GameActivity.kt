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

        // Get category and teams from the previous screen
        val category = intent.getStringExtra("category") ?: "Animals"
        val teamsList =
            intent.getSerializableExtra("teamsList") as? ArrayList<Team> ?: arrayListOf()

        // Create a new game instance
        game = Game(category, teamsList)

        // Set up the game screen
        setContent {
            GameScreen(
                game,
                onFinish = { result ->
                    // Go to the results screen
                    val intent = Intent(this, ResultsActivity::class.java)
                    intent.putExtra("category", category)
                    intent.putExtra("teamsList", ArrayList(teamsList))
                    intent.putExtra("result", result)
                    startActivity(intent)
                    finish()
                },
                onTeamsActivity = {
                    // Return to the teams screen
                    val intent = Intent(this, TeamsActivity::class.java)
                    intent.putExtra("category", category)
                    intent.putExtra("teamsList", ArrayList(teamsList))
                    startActivity(intent)
                    finish()
                }
            )
        }
    }

    @Composable
    fun GameScreen(game: Game, onFinish: (Int) -> Unit, onTeamsActivity: () -> Unit) {
        val wonderian = FontFamily(Font(R.font.wonderian))

        // Observe game data (seconds, team, word)
        val seconds by game.seconds
        val teamIndex by game.currentTeamIndex
        val word = game.selectedWord

        // Background color options
        val colors = listOf(
            Color(0xFFFFCDD2),
            Color(0xFFC8E6C9),
            Color(0xFFBBDEFB),
            Color(0xFFFFF9C4),
            Color(0xFFD1C4E9),
            Color(0xFFFFE0B2)
        )

        var showNextTeamScreen by remember { mutableStateOf(false) }
        var backgroundColor by remember { mutableStateOf(colors.random()) }

        // Change color each time the word changes
        LaunchedEffect(word) {
            backgroundColor = colors.random()
        }

        // Start the game timer
        LaunchedEffect(Unit) {
            game.startTimer { showNextTeamScreen = true }
        }

        // If the game is finished, show results
        if (game.gameFinished.value) {
            LaunchedEffect(Unit) {
                val result: Int = game.results()
                onFinish(result)
            }
        }

        if (showNextTeamScreen) {
            // Show next team screen or results if all played
            if (teamIndex + 1 >= game.teams.size) {
                LaunchedEffect(Unit) {
                    val result: Int = game.results()
                    onFinish(result)
                }
            } else {
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
            // Main gameplay screen
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .padding(16.dp)
            ) {
                // Show time, team, and points
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

                // Pause, restart, and continue buttons
                var showContinue by remember { mutableStateOf(false) }
                var p by remember { mutableStateOf(true) }

                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 24.dp, end = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ImagenBoton(
                        drawableId = R.drawable.pause,
                        modifier = Modifier.size(50.dp)
                    ) {
                        game.stopTimer()
                        showContinue = true
                        p = false
                    }

                    ImagenBoton(
                        drawableId = R.drawable.restart,
                        modifier = Modifier.size(50.dp)
                    ) {
                        game.resetTimer()
                        game.startTimer { showNextTeamScreen = true }
                        showContinue = false
                        p = true
                    }

                    if (showContinue) {
                        ImagenBoton(
                            drawableId = R.drawable.restart,
                            modifier = Modifier.size(50.dp)
                        ) {
                            game.startTimer { showNextTeamScreen = true }
                            showContinue = false
                            p = true
                        }
                    }
                }

                // Main word and correct/wrong buttons
                if (p) {
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
                } else {
                    // Pause state emoji
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .padding(horizontal = 30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🙈",
                            fontSize = 100.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Home button to go back to team selection
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
                        game.stopTimer()
                        onTeamsActivity()
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

        // Screen that appears between turns
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
                    Text(
                        text = "Continue",
                        fontFamily = wonderian,
                        fontSize = 25.sp
                    )
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
        // Reusable image button with animation
        buttonAnimation(
            drawableId = drawableId,
            onClick = onClick,
            modifier = modifier
        )
    }
}
