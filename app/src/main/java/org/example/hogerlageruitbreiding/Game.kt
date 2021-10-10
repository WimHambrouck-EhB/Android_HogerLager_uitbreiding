package org.example.hogerlageruitbreiding

import java.util.*

class Game {
    enum class PlayType {
        LOWER, EQUAL, HIGHER
    }

    var score: Int = 0
        private set

    private var currentNumber = 0

    private val random: Random = Random()


    init {
        currentNumber = getRandomNumber()
    }

    private fun getRandomNumber(): Int {
        return random.nextInt(12) + 1
    }

    private fun getAnswer(userGuess: Int): PlayType {
        return when {
            currentNumber > userGuess -> PlayType.HIGHER
            currentNumber < userGuess -> PlayType.LOWER
            else -> PlayType.EQUAL
        }
    }

    fun play(userGuess: Int): Game.PlayType {
        val playType = getAnswer(userGuess)

        if (playType == Game.PlayType.EQUAL) {
            score++
            currentNumber = getRandomNumber()
        }

        return playType
    }
}