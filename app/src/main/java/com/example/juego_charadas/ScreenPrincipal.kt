package com.example.juego_charadas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ScreenPrincipal(navController: NavController) {
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
        // Cuadro 2x2 de botones
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ImagenBoton(R.drawable.btn2) { /* acción botón 1 */ }
                ImagenBoton(R.drawable.btn3) { /* acción botón 2 */ }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ImagenBoton(R.drawable.btn4) { /* acción botón 3 */ }
                ImagenBoton(R.drawable.btn5) { /* acción botón 4 */ }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        // Botón central de inicio → navega a la pantalla del juego
        ImagenBoton(R.drawable.incio) {
            navController.navigate("game")
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
