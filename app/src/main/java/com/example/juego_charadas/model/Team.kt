package com.example.juego_charadas.model

import java.io.Serializable

data class Team(
    val players: Int,
    var points: Int = 0
): Serializable



