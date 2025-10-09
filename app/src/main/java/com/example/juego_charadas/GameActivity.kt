package com.example.juego_charadas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

class GameActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val teams = intent.getIntExtra("teams", 2)
            GameScreen(teams)
        }
    }

    @Composable
    fun GameScreen(teams: Int) {
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
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "¡Bienvenido, $teams" + "!")

                Spacer(modifier = Modifier.height(24.dp))

                ImagenBoton(R.drawable.incio) {
                    finish()
                }
            }
        }
    }

    @Composable
    fun ImagenBoton(drawableId: Int, onClick: () -> Unit) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .clickable { onClick() },
            contentScale = ContentScale.Fit
        )
    }
}
