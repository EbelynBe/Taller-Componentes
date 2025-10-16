package com.example.juego_charadas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.ui.draw.scale
import com.example.juego_charadas.model.Team

// This activity shows a countdown before the game starts
class CountdownActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get data from previous activity
        val teamsList = intent.getSerializableExtra("teamsList") as? ArrayList<Team> ?: arrayListOf()
        val category = intent.getStringExtra("category") ?: "Sin categoría"
        val teams = intent.getIntExtra("teams", 2)

        // Set the UI content with the countdown screen
        setContent {
            CountdownScreen(
                onFinish = {
                    // When countdown finishes, start the game activity
                    val intent = Intent(this, GameActivity::class.java)
                    intent.putExtra("teamsList", teamsList)
                    intent.putExtra("category", category)
                    intent.putExtra("teams", teams)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}

@Composable
fun CountdownScreen(onFinish: () -> Unit) {
    var currentCount by remember { mutableStateOf(3) } // countdown starts from 3

    // Custom font (Wonderian)
    val wonderian = FontFamily(Font(R.font.wonderian))

    // Smooth zoom animation for the text
    val scale by animateFloatAsState(
        targetValue = 1.5f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
    )

    // Countdown logic (runs automatically)
    LaunchedEffect(Unit) {
        for (i in 3 downTo 1) { // counts from 3 to 1
            currentCount = i
            delay(1000) // wait 1 second between numbers
        }
        currentCount = 0
        delay(500) // small pause before starting game
        onFinish() // go to next screen
    }

    // Background color gradient (pink → purple)
    val backgroundGradient = Brush.linearGradient(
        colors = listOf(Color(0xFFFF80AB), Color(0xFF7B1FA2))
    )

    // Main container with centered text
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        // Displays countdown number or "Let's go!!"
        Text(
            text = if (currentCount > 0) currentCount.toString() else "¡Let's go!!",
            fontSize = 100.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = wonderian,
            color = Color.White,
            modifier = Modifier.scale(scale)
        )
    }
}
