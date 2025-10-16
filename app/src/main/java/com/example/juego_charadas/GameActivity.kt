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

        // Get category and teams list
        val category = intent.getStringExtra("category") ?: "Animals"
        val teamsList =
            intent.getSerializableExtra("teamsList") as? ArrayList<Team> ?: arrayListOf()

        game = Game(category, teamsList)

        setContent {
            GameScreen(game, onFinish = {
                val intent = Intent(this, ResultsActivity::class.java)
                intent.putExtra("category", category)
                startActivity(intent)
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
            Color(0xFFFFCDD2),
            Color(0xFFC8E6C9),
            Color(0xFFBBDEFB),
            Color(0xFFFFF9C4),
            Color(0xFFD1C4E9),
            Color(0xFFFFE0B2)
        )

        var showNextTeamScreen by remember { mutableStateOf(false) }
        var backgroundColor by remember { mutableStateOf(colors.random()) }

        // Change background color when word changes
        LaunchedEffect(word) {
            backgroundColor = colors.random()
        }

        // Start game timer
        LaunchedEffect(Unit) {
            game.startTimer { showNextTeamScreen = true }
        }

        // Go to results if game ends
        if (game.gameFinished.value) {
            LaunchedEffect(Unit) {
                onFinish()
            }
        }

        if (showNextTeamScreen) {
            if (teamIndex + 1 >= game.teams.size) {
                LaunchedEffect(Unit) {
                    onFinish()
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .padding(16.dp)
            ) {
                // Display team info and time
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

                // Pause and restart buttons in a row
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
                    }

                    ImagenBoton(
                        drawableId = R.drawable.restart,
                        modifier = Modifier.size(50.dp)
                    ) {
                        game.resetTimer()
                        game.startTimer { showNextTeamScreen = true }
                    }
                }

                // Display word and correct/wrong buttons
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

                // Home button
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
                // Display next turn text
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
                // Continue button
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
        // Button with image
        buttonAnimation(
            drawableId = drawableId,
            onClick = onClick,
            modifier = modifier
        )
    }
}
