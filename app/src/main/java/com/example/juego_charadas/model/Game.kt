package com.example.juego_charadas.model

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable

class Game(private val category: String, private val teamsList: MutableList<Team>) : Serializable {
    val teams get() = teamsList
    private val timeLimit = 60
    private var job: Job? = null

    // mejor: _gameFinished privado, gameFinished expuesto como State
    private val _gameFinished = mutableStateOf(false)
    val gameFinished: State<Boolean> get() = _gameFinished

    private val _seconds = mutableStateOf(0)
    val seconds: MutableState<Int> get() = _seconds

    private val _isRunning = mutableStateOf(false)
    val isRunning: MutableState<Boolean> get() = _isRunning

    private val _currentTeamIndex = mutableStateOf(0)
    val currentTeamIndex: MutableState<Int> get() = _currentTeamIndex
    val currentTeam: Team
        get() = teamsList[_currentTeamIndex.value]

    // listas...
    private val animals = listOf("Dog", "Cat", "Elephant", "Lion", "Tiger", "Monkey", "Horse", "Rabbit")
    private val food = listOf("Pizza", "Burger", "Salad", "Pasta", "Ice cream", "Sushi", "Hot dog", "Cake")
    private val professions = listOf("Doctor", "Teacher", "Engineer", "Firefighter", "Pilot", "Chef", "Police officer", "Musician")
    private val movies = listOf("Titanic", "Avatar", "Frozen", "Inception", "Spiderman", "Toy Story", "The Lion King", "Batman")
    private val actors = listOf("dana", "hola")

    private val selectedWordList = when (category) {
        "Animals" -> animals
        "Food" -> food
        "Professions" -> professions
        "Movies" -> movies
        "Actors" -> actors
        else -> animals
    }

    var selectedWord by mutableStateOf(selectedWordList.random())
        private set

    fun nextWord() {
        currentTeam.points++  // sumas un punto al equipo
        selectedWord = selectedWordList.random()

        // Log para depurar los puntos
        teamsList.forEachIndexed { index, team ->
            Log.d("GameDebug", "Equipo ${index + 1}: ${team.points} puntos")
        }
    }

    fun startTimer(onTimeOver: () -> Unit = {}) {
        if (isRunning.value) return
        isRunning.value = true
        // reiniciamos segundos al comenzar
        seconds.value = 0

        job = CoroutineScope(Dispatchers.Default).launch {
            while (isRunning.value && seconds.value < timeLimit) {
                delay(1000)
                seconds.value++

                if (seconds.value >= timeLimit) {
                    isRunning.value = false
                    withContext(Dispatchers.Main) {
                        onTimeOver()
                    }
                }
            }
        }
    }

    fun stopTimer() {
        isRunning.value = false
        job?.cancel()
        job = null
    }

    fun resetTimer() {
        stopTimer()
        seconds.value = 0
    }

    fun nextTeam() {
        job?.cancel()
        job = null

        if (currentTeamIndex.value == teamsList.size - 1) {
            CoroutineScope(Dispatchers.Main).launch {
                _gameFinished.value = true
                stopTimer()
            }
        } else {
            currentTeamIndex.value++
            seconds.value = 0
            startTimer { nextTeam() }
        }
    }
}
