package com.example.juego_charadas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
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
import com.example.juego_charadas.ui.theme.buttonAnimation

class TeamsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val teams = intent.getIntExtra("teams", 2)
        val category = intent.getStringExtra("category") ?: "Sin categoría"

        val customFont = FontFamily(Font(R.font.wonderian))

        setContent {
            TeamsSelector(
                initialTeams = teams,
                categoryName = category,
                customFont = customFont,
                onBack = { finish() },
                onStartGame = {
                    val intent = Intent(this, CountdownActivity::class.java)
                    intent.putExtra("teams", teams)
                    intent.putExtra("category", category)
                    startActivity(intent)
                }

            )
        }
    }
}

@Composable
fun TeamsSelector(
    initialTeams: Int = 2,
    categoryName: String,
    customFont: FontFamily,
    onBack: () -> Unit,
    onStartGame: () -> Unit
) {
    var teams by remember { mutableStateOf(initialTeams) }

    val teamColors = listOf("Orange", "Blue", "Purple", "Green", "Red")
    val visibleTeams = teamColors.take(teams)

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
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Category: $categoryName",
                fontSize = 32.sp,
                fontFamily = customFont,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(30.dp))


            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                itemsIndexed(visibleTeams) { index, colorName ->
                    val drawableId = when (colorName) {
                        "Orange" -> R.drawable.background_orange
                        "Blue" -> R.drawable.background_blue
                        "Purple" -> R.drawable.background_purple
                        "Green" -> R.drawable.background_green
                        "Red" -> R.drawable.background_red
                        else -> R.drawable.background_orange
                    }

                    Box(
                        modifier = Modifier
                            .width(180.dp)
                            .height(160.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        buttonAnimation(
                            drawableId = drawableId,
                            modifier = Modifier.fillMaxSize(),
                            onClick = {

                            }
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Team ${index + 1}",
                                fontSize = 28.sp,
                                fontFamily = customFont,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Players",
                                fontSize = 20.sp,
                                fontFamily = customFont,
                                color = Color.White
                            )
                            Text(
                                text = "Points",
                                fontSize = 20.sp,
                                fontFamily = customFont,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            buttonAnimation(
                drawableId = R.drawable.backbutton,
                onClick = onBack,
                modifier = Modifier.size(90.dp)
            )
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            buttonAnimation(
                drawableId = R.drawable.incio,
                onClick = onStartGame,
                modifier = Modifier.size(90.dp)
            )
        }
    }
}
