package com.example.juego_charadas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.juego_charadas.model.Game
import com.example.juego_charadas.model.Team
import com.example.juego_charadas.ui.theme.buttonAnimation



class GameActivity : ComponentActivity() {
    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val teams = intent.getIntExtra("teams", 2)
        val category = intent.getStringExtra("category") ?: "Animals"
        val teamsList = intent.getSerializableExtra("teamsList") as? ArrayList<Team> ?: arrayListOf()
        game = Game(category, teamsList)

        setContent {
            GameScreen(game, onFinish = {
                val intent = Intent(this, ResultsActivity::class.java)
                //intent.putExtra("teamsList", ArrayList(game.teams))
                startActivity(intent)
                finish()
            })
        }
    }


    @Composable
    fun GameScreen(game: Game, onFinish: () -> Unit) {
        val wonderian = FontFamily(Font(R.font.wonderian))
        val context = LocalContext.current

        val seconds by game.seconds
        val teamIndex by game.currentTeamIndex
        val word = game.selectedWord

        if (game.gameFinished.value) {
            LaunchedEffect(Unit) {
                onFinish()
            }
        }

        val colors = listOf(
            Color(0xFFFFCDD2),
            Color(0xFFC8E6C9),
            Color(0xFFBBDEFB),
            Color(0xFFFFF9C4),
            Color(0xFFD1C4E9),
            Color(0xFFFFE0B2)
        )

        var backgroundColor by remember { mutableStateOf(colors.random()) }

        LaunchedEffect(word) {
            backgroundColor = colors.random()
        }

        LaunchedEffect(Unit) {
            game.startTimer {
                game.nextTeam()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(16.dp)
        ) {

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
            }

            Column(
                modifier = Modifier.align(Alignment.TopEnd),
                horizontalAlignment = Alignment.End
            ) {
                Button(onClick = { game.stopTimer() }) {
                    Text("Pause")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { game.resetTimer() }) {
                    Text("Restart")
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = word,
                    fontFamily = wonderian,
                    fontSize = 100.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {
                    game.nextWord()
                }) {
                    Text("Next word")
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                ImagenBoton(R.drawable.incio, Modifier.size(80.dp)) {
                    finish()
                }
            }
        }
    }


    @Composable
    fun ImageButton(drawableId: Int, onClick: () -> Unit) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clickable { onClick() },
            contentScale = ContentScale.Fit
        )
        @Composable
        fun ImagenBoton(drawableId: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
            buttonAnimation(drawableId = drawableId, onClick = onClick, modifier = modifier)
        }
    }
    @Composable
    fun ImagenBoton(drawableId: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
        buttonAnimation(drawableId = drawableId, onClick = onClick, modifier = modifier)
    }

}
