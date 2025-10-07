package com.example.juego_charadas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.juego_charadas.ui.theme.Juego_CharadasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Juego_CharadasTheme {
                PantallaPrincipal()
            }
        }
    }
}

@Composable
fun PantallaPrincipal() {
    Image(
        painter = painterResource(id = R.drawable.fondo), // tu imagen de fondo
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop // ajusta para cubrir toda la pantalla
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

        // Botón central de inicio
        ImagenBoton(R.drawable.incio) { /* acción botón inicio */ }
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
