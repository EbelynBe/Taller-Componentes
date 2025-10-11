package com.example.juego_charadas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.juego_charadas.model.Category
import com.example.juego_charadas.ui.theme.buttonAnimation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenPrincipal()
        }
    }

    @Composable
    fun ScreenPrincipal() {
        var selectedCategory by remember { mutableStateOf(Category.Animals) }
        var teams by remember { mutableStateOf(2) }

        // Estados de jugadores
        var playersPerTeam by remember { mutableStateOf(calculateMinPlayers(teams)) }
        var minPlayers by remember { mutableStateOf(calculateMinPlayers(teams)) }

        // 🔁 Si cambia el número de equipos, se actualiza el mínimo y se reinicia la cantidad
        LaunchedEffect(teams) {
            minPlayers = calculateMinPlayers(teams)
            playersPerTeam = minPlayers
        }

        val customFont = FontFamily(Font(R.font.wonderian))
        val colorSeleccionado = Color(android.graphics.Color.parseColor("#1798C9"))
        val colorNormal = Color(android.graphics.Color.parseColor("#1EC0FF"))
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

            // 🟣 Caja morada (categorías)
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf(
                        "Food" to Category.Food,
                        "Movies" to Category.Movies,
                        "Professions" to Category.Professions,
                        "Animals" to Category.Animals
                    ).forEach { (text, category) ->
                        CategoryButton(
                            text = text,
                            isSelected = selectedCategory == category,
                            customFont = customFont,
                            fontSize = sizeCategoria,
                            selectedColor = colorSeleccionado,
                            normalColor = colorNormal
                        ) { selectedCategory = category }
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
                    // ➖ Flecha izquierda
                    ImagenBoton(
                        drawableId = R.drawable.left,
                        modifier = Modifier.size(45.dp)
                    ) {
                        if (playersPerTeam > minPlayers) playersPerTeam--
                    }

                    // Número de jugadores
                    Text(
                        text = "$playersPerTeam",
                        fontSize = playerTextSize,
                        fontFamily = customFont,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    // ➕ Flecha derecha
                    ImagenBoton(
                        drawableId = R.drawable.right,
                        modifier = Modifier.size(45.dp)
                    ) {
                        playersPerTeam++
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
                        TeamButton(2, teams == 2, customFont, sizeEquipos, colorSeleccionado, colorNormal) {
                            teams = 2
                        }
                        TeamButton(3, teams == 3, customFont, sizeEquipos, colorSeleccionado, colorNormal) {
                            teams = 3
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        TeamButton(4, teams == 4, customFont, sizeEquipos, colorSeleccionado, colorNormal) {
                            teams = 4
                        }
                        TeamButton(5, teams == 5, customFont, sizeEquipos, colorSeleccionado, colorNormal) {
                            teams = 5
                        }
                    }
                }
            }

            // 🔘 Botón de inicio
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-20).dp)
            ) {
                ImagenBoton(R.drawable.incio, Modifier.size(80.dp)) {
                    val intent = Intent(this@MainActivity, TeamsActivity::class.java)
                    intent.putExtra("teams", teams)
                    intent.putExtra("category", selectedCategory.name)
                    intent.putExtra("players", playersPerTeam)
                    startActivity(intent)
                }
            }
        }
    }

    @Composable
    fun ImagenBoton(drawableId: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
        buttonAnimation(drawableId = drawableId, onClick = onClick, modifier = modifier)
    }

    fun calculateMinPlayers(teams: Int): Int {
        return when (teams) {
            2 -> 4
            3 -> 6
            4 -> 8
            5 -> 10
            else -> 4
        }
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