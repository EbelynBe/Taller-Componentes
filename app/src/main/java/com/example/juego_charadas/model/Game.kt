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

// This class manages the main game logic
class Game(private val category: String, private val teamsList: MutableList<Team>) : Serializable {
    val teams get() = teamsList
    private val timeLimit = 50 // time limit for each round (in seconds)
    private var job: Job? = null // background coroutine job for timer

    // Indicates if the game is finished
    private val _gameFinished = mutableStateOf(false)
    val gameFinished: State<Boolean> get() = _gameFinished

    // Holds the number of seconds elapsed
    private val _seconds = mutableStateOf(0)
    val seconds: MutableState<Int> get() = _seconds

    // Indicates if the timer is currently running
    private val _isRunning = mutableStateOf(false)
    val isRunning: MutableState<Boolean> get() = _isRunning

    // Keeps track of which team's turn it is
    private val _currentTeamIndex = mutableStateOf(0)
    val currentTeamIndex: MutableState<Int> get() = _currentTeamIndex
    val currentTeam: Team
        get() = teamsList[_currentTeamIndex.value]

    // Lists of words for each category
    private val animals = listOf(
        "Dog", "Cat", "Elephant", "Lion", "Tiger", "Monkey", "Horse", "Rabbit",
        "Giraffe", "Zebra", "Kangaroo", "Panda", "Crocodile", "Dolphin", "Fox",
        "Wolf", "Bear", "Snake", "Owl", "Penguin", "Frog", "Camel", "Sheep"
    )

    private val food = listOf(
        "Pizza", "Burger", "Salad", "Pasta", "Ice cream", "Sushi", "Hot dog", "Cake",
        "Sandwich", "Taco", "Soup", "Steak", "Donut", "Chocolate", "Rice",
        "Bread", "Cheese", "Fish", "Chicken", "Fries", "Pancake", "Cookie", "Apple"
    )

    private val professions = listOf(
        "Doctor", "Teacher", "Engineer", "Firefighter", "Pilot", "Chef", "Police officer", "Musician",
        "Artist", "Nurse", "Scientist", "Actor", "Farmer", "Plumber", "Electrician",
        "Carpenter", "Dentist", "Lawyer", "Photographer", "Journalist", "Mechanic", "Architect", "Driver"
    )

    private val movies = listOf(
        "Titanic", "Avatar", "Frozen", "Inception", "Spiderman", "Toy Story", "The Lion King", "Batman",
        "Jurassic Park", "Harry Potter", "The Avengers", "The Matrix", "Shrek", "Up", "Coco",
        "Black Panther", "The Incredibles", "Finding Nemo", "Iron Man", "Thor", "Aladdin", "Moana", "Encanto"
    )

    private val actors = listOf(
        "Leonardo DiCaprio", "Emma Stone", "Tom Hanks", "Zendaya", "Brad Pitt", "Natalie Portman", "Will Smith", "Anne Hathaway",
        "Robert Downey Jr.", "Scarlett Johansson", "Chris Hemsworth", "Morgan Freeman", "Jennifer Lawrence", "Johnny Depp", "Dwayne Johnson",
        "Angelina Jolie", "Keanu Reeves", "Ryan Reynolds", "Julia Roberts", "Samuel L. Jackson", "Meryl Streep", "Matt Damon", "Hugh Jackman"
    )

    // Selects which word list to use based on the chosen category
    private val selectedWordList = when (category) {
        "Animals" -> animals
        "Food" -> food
        "Professions" -> professions
        "Movies" -> movies
        "Actors" -> actors
        else -> animals
    }

    // The current word displayed in the game
    var selectedWord by mutableStateOf(selectedWordList.random())
        private set

    // Changes to the next word and optionally adds a point to the current team
    fun nextWord(num: Int) {
        if (num == 1) {
            currentTeam.points++
        }
        selectedWord = selectedWordList.random()

        // Log points for debugging
        teamsList.forEachIndexed { index, team ->
            Log.d("GameDebug", "Team ${index + 1}: ${team.points} points")
        }
    }

    // Starts the timer for the round and calls onTimeOver when it ends
    fun startTimer(onTimeOver: () -> Unit = {}) {
        if (isRunning.value) return
        isRunning.value = true

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

    // Stops the timer
    fun stopTimer() {
        isRunning.value = false
        job?.cancel()
        job = null
    }

    // Resets the timer and sets seconds back to 0
    fun resetTimer() {
        stopTimer()
        seconds.value = 0
    }

    // Moves to the next team's turn or ends the game if all have played
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

    // Calculates which team has the highest score and returns its index
    fun results(): Int {
        var maxPoints = -1
        var winningTeamIndex = -1

        for ((index, team) in teamsList.withIndex()) {
            if (team.points > maxPoints) {
                maxPoints = team.points
                winningTeamIndex = index
            } else if (team.points == maxPoints) {
                winningTeamIndex = -1
            }
        }
        return winningTeamIndex + 1
    }
}
