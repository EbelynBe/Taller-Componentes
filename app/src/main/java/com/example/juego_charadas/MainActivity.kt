package com.example.juego_charadas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.juego_charadas.model.Category

class MainActivity : ComponentActivity() {
    private var selectedCategory: Category = Category.Animals
    private var teams: Int = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenPrincipal()
        }
    }

    @Composable
    fun ScreenPrincipal() {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ImagenBoton(R.drawable.btn2) {
                        CalculatePlayers(4)
                    }
                    ImagenBoton(R.drawable.btn3) {
                        CalculatePlayers(6)
                        teams = 3
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ImagenBoton(R.drawable.btn4) {
                        CalculatePlayers(8)
                        teams = 4
                    }
                    ImagenBoton(R.drawable.btn5) {
                        CalculatePlayers(10)
                        teams = 5
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            ImagenBoton(R.drawable.incio) {
                val intent = Intent(this@MainActivity, GameActivity()::class.java)
                intent.putExtra("teams", teams)
                intent.putExtra("category", selectedCategory.name)
                startActivity(intent)
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

    fun CalculatePlayers(players: Int) {

    }
}
