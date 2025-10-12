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

class CountdownActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val teams = intent.getIntExtra("teams", 2)
        val category = intent.getStringExtra("category")

        setContent {
            CountdownScreen(
                onFinish = {
                    val intent = Intent(this, GameActivity::class.java)
                    intent.putExtra("teams", teams)
                    intent.putExtra("category", category)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}

@Composable
fun CountdownScreen(onFinish: () -> Unit) {
    var currentCount by remember { mutableStateOf(3) }

    // 🎨 Fuente personalizada Wonderian
    val wonderian = FontFamily(Font(R.font.wonderian))

    // Animación de zoom suave
    val scale by animateFloatAsState(
        targetValue = 1.5f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
    )

    // Efecto de cuenta regresiva
    LaunchedEffect(Unit) {
        for (i in 3 downTo 1) {
            currentCount = i
            delay(1000)
        }
        currentCount = 0
        delay(500)
        onFinish()
    }

    // 🎨 Fondo con degradado rosado → morado
    val backgroundGradient = Brush.linearGradient(
        colors = listOf(Color(0xFFFF80AB), Color(0xFF7B1FA2))
    )

    // Fondo + texto centrado
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (currentCount > 0) currentCount.toString() else "¡Let's go!!",
            fontSize = 100.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = wonderian, // 👈 Aplicamos la fuente Wonderian
            color = Color.White,
            modifier = Modifier.scale(scale)
        )
    }
}
