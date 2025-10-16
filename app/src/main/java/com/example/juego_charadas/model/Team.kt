package com.example.juego_charadas.model

import java.io.Serializable
// Represents a team in the game.
// Each team has a number of players and keeps track of its score (points).
data class Team(
    val players: Int,  // number of players in the team
    var points: Int = 0  // current score, starts at 0
): Serializable



