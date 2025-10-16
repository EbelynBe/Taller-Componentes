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
        setContent { ScreenPrincipal() } // Load main screen
    }

    @Composable
    fun ScreenPrincipal() {
        // State variables
        var selectedCategory by remember { mutableStateOf(Category.Animals) }
        var teams by remember { mutableStateOf(2) }
        var minPlayers by remember { mutableStateOf(calculateMinPlayers(teams)) }
        var totalPlayers by remember { mutableStateOf(minPlayers) }

        // Recalculate players when team count changes
        LaunchedEffect(teams) {
            minPlayers = calculateMinPlayers(teams)
            totalPlayers = minPlayers
        }

        // Custom font and colors
        val customFont = FontFamily(Font(R.font.wonderian))
        val colorSelected = Color("#1798C9".toColorInt())
        val colorNormal = Color("#1EC0FF".toColorInt())

        // Background image
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Title image
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 2.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.charadas),
                contentDescription = "Title Charades",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .aspectRatio(2f),
                contentScale = ContentScale.Fit
            )
        }

        // Main content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {

            // --- Category selection ---
            Box(
                modifier = Modifier
                    .size(240.dp, 200.dp)
                    .offset(x = (-80).dp, y = (-220).dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.purplebackground),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                Text(
                    text = "CATEGORY",
                    fontSize = 26.sp,
                    fontFamily = customFont,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = (-30).dp)
                )

                val categories = Category.values()
                var currentIndex by remember { mutableStateOf(0) }

                // Track current category
                LaunchedEffect(selectedCategory) {
                    currentIndex = categories.indexOf(selectedCategory)
                }

                // Arrows and text
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left arrow
                    ImagenBoton(R.drawable.left, Modifier.size(37.dp)) {
                        currentIndex = if (currentIndex > 0) currentIndex - 1 else categories.size - 1
                        selectedCategory = categories[currentIndex]
                    }

                    // Category name
                    Text(
                        text = categories[currentIndex].name,
                        fontSize = 27.sp,
                        fontFamily = customFont,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    // Right arrow
                    ImagenBoton(R.drawable.right, Modifier.size(37.dp)) {
                        currentIndex = if (currentIndex < categories.size - 1) currentIndex + 1 else 0
                        selectedCategory = categories[currentIndex]
                    }
                }
            }

            // --- Player selection ---
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

                Text(
                    text = "PLAYERS",
                    fontSize = 25.sp,
                    fontFamily = customFont,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = (-40).dp)
                )

                // Arrows and player number
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Decrease players
                    ImagenBoton(R.drawable.left, Modifier.size(45.dp)) {
                        if (totalPlayers > minPlayers) totalPlayers--
                    }

                    // Player number
                    Text(
                        text = "$totalPlayers",
                        fontSize = 70.sp,
                        fontFamily = customFont,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    // Increase players
                    ImagenBoton(R.drawable.right, Modifier.size(45.dp)) {
                        totalPlayers++
                    }
                }
            }

            // --- Team selection ---
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

                Text(
                    text = "TEAMS",
                    fontSize = 25.sp,
                    fontFamily = customFont,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = (-40).dp)
                )

                // Buttons grid
                Column(
                    modifier = Modifier.size(160.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        TeamButton(2, teams == 2, customFont, 50.sp, colorSelected, colorNormal) { teams = 2 }
                        TeamButton(3, teams == 3, customFont, 50.sp, colorSelected, colorNormal) { teams = 3 }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        TeamButton(4, teams == 4, customFont, 50.sp, colorSelected, colorNormal) { teams = 4 }
                        TeamButton(5, teams == 5, customFont, 50.sp, colorSelected, colorNormal) { teams = 5 }
                    }
                }
            }

            // --- Start button ---
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-20).dp)
            ) {
                // Go to TeamsActivity
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

    // Calculate minimum players per team
    fun calculateMinPlayers(teams: Int): Int = teams * 2

    // Wrapper for animated buttons
    @Composable
    fun ImagenBoton(drawableId: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
        buttonAnimation(drawableId = drawableId, onClick = onClick, modifier = modifier)
    }

    // Category button (not used here)
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

    // Button for selecting team number
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
