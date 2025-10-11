package com.example.juego_charadas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.juego_charadas.ui.theme.buttonAnimation

@Composable
fun ScreenPrincipal(navController: NavController) {

    Image(
        painter = painterResource(id = R.drawable.fondo),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.charadas),
            contentDescription = null,
            modifier = Modifier
                .size(350.dp)

        )
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-80).dp, y = (-230).dp),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.purplebackground),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )




            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                ImagenBoton(R.drawable.categoryfood, modifier = Modifier.size(60.dp)) {

                }
                ImagenBoton(R.drawable.categorymovies, modifier = Modifier.size(60.dp)) {

                }
                ImagenBoton(R.drawable.categoryprofessions, modifier = Modifier.size(60.dp)) {

                }
                ImagenBoton(R.drawable.categoryanimal, modifier = Modifier.size(60.dp)) {

                }
            }
        }


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
        }


        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = 80.dp, y = -2.dp),
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
                    ImagenBoton(R.drawable.btn2, modifier = Modifier.size(70.dp)) { }
                    ImagenBoton(R.drawable.btn3, modifier = Modifier.size(70.dp)) { }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    ImagenBoton(R.drawable.btn4, modifier = Modifier.size(70.dp)) { }
                    ImagenBoton(R.drawable.btn5, modifier = Modifier.size(70.dp)) { }
                }
            }
        }


        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-20).dp)
        ) {
            ImagenBoton(R.drawable.incio, modifier = Modifier.size(80.dp)) {
                navController.navigate("game")
            }
        }
    }
}


@Composable
fun ImagenBoton(drawableId: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    buttonAnimation(
        drawableId = drawableId,
        onClick = onClick,
        modifier = modifier
    )
}
