package com.example.juego_charadas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.core.graphics.toColorInt
import com.example.juego_charadas.model.Category
import com.example.juego_charadas.ui.theme.buttonAnimation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ScreenPrincipal() }
    }

    @Composable
    fun ScreenPrincipal() {
        var selectedCategory by remember { mutableStateOf(Category.Animals) }
        var teams by remember { mutableStateOf(2) }

        // 🎯 Calcular mínimo según cantidad de equipos
        var minPlayers by remember { mutableStateOf(calculateMinPlayers(teams)) }
        var totalPlayers by remember { mutableStateOf(minPlayers) }

        LaunchedEffect(teams) {
            minPlayers = calculateMinPlayers(teams)
            totalPlayers = minPlayers
        }

        val customFont = FontFamily(Font(R.font.wonderian))
        val colorSeleccionado = Color("#1798C9".toColorInt())
        val colorNormal = Color("#1EC0FF".toColorInt())

        val sizeCategoria = 22.sp
        val sizeEquipos = 50.sp
        val playerTextSize = 70.sp

        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .size(230.dp, 260.dp)
                    .offset(x = (-80).dp, y = (-230).dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.purplebackground),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                // Lista de categorías disponibles
                val categories = Category.values()
                var currentIndex by remember { mutableStateOf(0) }

                // Sincronizar con el valor actual
                LaunchedEffect(selectedCategory) {
                    currentIndex = categories.indexOf(selectedCategory)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Flecha izquierda
                    ImagenBoton(
                        drawableId = R.drawable.left,
                        modifier = Modifier.size(37.dp)
                    ) {
                        currentIndex = if (currentIndex > 0) currentIndex - 1 else categories.size - 1
                        selectedCategory = categories[currentIndex]
                    }

                    // Nombre de categoría (animado)
                    Text(
                        text = categories[currentIndex].name,
                        fontSize = 27.sp,
                        fontFamily = customFont,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    // Flecha derecha
                    ImagenBoton(
                        drawableId = R.drawable.right,
                        modifier = Modifier.size(37.dp)
                    ) {
                        currentIndex = if (currentIndex < categories.size - 1) currentIndex + 1 else 0
                        selectedCategory = categories[currentIndex]
                    }
                }
            }

            // 🟡 Caja amarilla (jugadores)
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .offset(x = -80.dp, y = 227.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.yellowbackground),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ImagenBoton(R.drawable.left, Modifier.size(45.dp)) {
                        // 👇 No permitir bajar del mínimo
                        if (totalPlayers > minPlayers) totalPlayers--
                    }

                    Text(
                        text = "$totalPlayers",
                        fontSize = playerTextSize,
                        fontFamily = customFont,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    ImagenBoton(R.drawable.right, Modifier.size(45.dp)) {
                        totalPlayers++ // 👆 Aumenta de uno en uno
                    }
                }
            }

            // 🔵 Caja azul (equipos)
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .offset(x = 80.dp, y = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fr),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.size(160.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        TeamButton(2, teams == 2, customFont, sizeEquipos, colorSeleccionado, colorNormal) { teams = 2 }
                        TeamButton(3, teams == 3, customFont, sizeEquipos, colorSeleccionado, colorNormal) { teams = 3 }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        TeamButton(4, teams == 4, customFont, sizeEquipos, colorSeleccionado, colorNormal) { teams = 4 }
                        TeamButton(5, teams == 5, customFont, sizeEquipos, colorSeleccionado, colorNormal) { teams = 5 }
                    }
                }
            }

            // 🟢 Botón de inicio
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-20).dp)
            ) {
                ImagenBoton(R.drawable.incio, Modifier.size(80.dp)) {
                    val intent = Intent(this@MainActivity, TeamsActivity::class.java)
                    intent.putExtra("teams", teams)
                    intent.putExtra("totalPlayers", totalPlayers)
                    intent.putExtra("category", selectedCategory.name)
                    startActivity(intent)
                }
            }
        }
    }

    // ✅ Cada equipo mínimo 2 jugadores → total mínimo = equipos * 2
    fun calculateMinPlayers(teams: Int): Int = teams * 2

    @Composable
    fun ImagenBoton(drawableId: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
        buttonAnimation(drawableId = drawableId, onClick = onClick, modifier = modifier)
    }

    @Composable
    fun CategoryButton(
        text: String,
        isSelected: Boolean,
        customFont: FontFamily,
        fontSize: androidx.compose.ui.unit.TextUnit,
        selectedColor: Color,
        normalColor: Color,
        onClick: () -> Unit
    ) {
        val buttonColor = if (isSelected) selectedColor else normalColor
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            shape = CircleShape,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(55.dp)
        ) {
            Text(
                text = text,
                fontSize = fontSize,
                fontFamily = customFont,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = Color.White
            )
        }
    }

    @Composable
    fun TeamButton(
        number: Int,
        isSelected: Boolean,
        customFont: FontFamily,
        fontSize: androidx.compose.ui.unit.TextUnit,
        selectedColor: Color,
        normalColor: Color,
        onClick: () -> Unit
    ) {
        val buttonColor = if (isSelected) selectedColor else normalColor
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            shape = RoundedCornerShape(17.dp),
            modifier = Modifier.size(70.dp)
        ) {
            Text(
                text = number.toString(),
                fontSize = fontSize,
                fontFamily = customFont,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = Color.White
            )
        }
    }
}
