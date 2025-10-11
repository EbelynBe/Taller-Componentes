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

        var selectedCategory by remember { mutableStateOf(Category.Animals) }
        var teams by remember { mutableStateOf(2) }

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


            var playersPerTeam by remember { mutableStateOf(2) }
            val minPlayers = 2

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

                    ImagenBoton(
                        drawableId = R.drawable.left,
                        modifier = Modifier.size(45.dp)
                    ) {
                        if (playersPerTeam > minPlayers) playersPerTeam--
                        CalculatePlayers(playersPerTeam)
                    }


                    Text(
                        text = "$playersPerTeam",
                        fontSize = playerTextSize,
                        fontFamily = customFont,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )


                    ImagenBoton(
                        drawableId = R.drawable.right,
                        modifier = Modifier.size(45.dp)
                    ) {
                        playersPerTeam++
                        CalculatePlayers(playersPerTeam)
                    }
                }
            }


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
                        TeamButton(
                            number = 2,
                            isSelected = teams == 2,
                            customFont = customFont,
                            fontSize = sizeEquipos,
                            selectedColor = colorSeleccionado,
                            normalColor = colorNormal
                        ) {
                            teams = 2
                            CalculatePlayers(4)
                        }

                        TeamButton(
                            number = 3,
                            isSelected = teams == 3,
                            customFont = customFont,
                            fontSize = sizeEquipos,
                            selectedColor = colorSeleccionado,
                            normalColor = colorNormal
                        ) {
                            teams = 3
                            CalculatePlayers(6)
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        TeamButton(
                            number = 4,
                            isSelected = teams == 4,
                            customFont = customFont,
                            fontSize = sizeEquipos,
                            selectedColor = colorSeleccionado,
                            normalColor = colorNormal
                        ) {
                            teams = 4
                            CalculatePlayers(8)
                        }

                        TeamButton(
                            number = 5,
                            isSelected = teams == 5,
                            customFont = customFont,
                            fontSize = sizeEquipos,
                            selectedColor = colorSeleccionado,
                            normalColor = colorNormal
                        ) {
                            teams = 5
                            CalculatePlayers(10)
                        }
                    }
                }
            }


            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-20).dp)
            ) {
                ImagenBoton(R.drawable.incio, Modifier.size(80.dp)) {
                    val intent = Intent(this@MainActivity, TeamsActivity::class.java)
                    intent.putExtra("teams", teams)
                    intent.putExtra("category", selectedCategory.name)
                    startActivity(intent)
                }
            }
        }
    }


    @Composable
    fun ImagenBoton(drawableId: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
        buttonAnimation(drawableId = drawableId, onClick = onClick, modifier = modifier)
    }

    fun CalculatePlayers(players: Int) {

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
