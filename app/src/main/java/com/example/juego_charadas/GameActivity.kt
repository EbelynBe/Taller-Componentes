package com.example.juego_charadas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.juego_charadas.ui.theme.buttonAnimation



class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val teams = intent.getIntExtra("teams", 2)
            GameScreen(teams)
        }
    }

    @Composable
    fun GameScreen(teams: Int) {
        val wonderian = FontFamily(Font(R.font.wonderian))

        val animals = listOf("Dog", "Cat", "Elephant", "Lion", "Tiger", "Monkey", "Horse", "Rabbit")
        val food = listOf("Pizza", "Burger", "Salad", "Pasta", "Ice cream", "Sushi", "Hot dog", "Cake")
        val professions = listOf("Doctor", "Teacher", "Engineer", "Firefighter", "Pilot", "Chef", "Police officer", "Musician")
        val movies = listOf("Titanic", "Avatar", "Frozen", "Inception", "Spiderman", "Toy Story", "The Lion King", "Batman")

        val selectedWord = professions.random()

        val colors = listOf(
            Color(0xFFFFCDD2),
            Color(0xFFC8E6C9),
            Color(0xFFBBDEFB),
            Color(0xFFFFF9C4),
            Color(0xFFD1C4E9),
            Color(0xFFFFE0B2)
        )
        val backgroundColor = colors.random()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = selectedWord,
                    fontFamily = wonderian,
                    fontSize = 100.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

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
