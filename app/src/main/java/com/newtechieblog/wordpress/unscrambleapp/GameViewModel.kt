package com.newtechieblog.wordpress.unscrambleapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.newtechieblog.wordpress.unscrambleapp.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel: ViewModel() {
    // Game UI
    private val _uiState = MutableStateFlow(GameUiState())
    // Backing property to avoid state updates from other classes
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set

    // Set of words used in the game
    private var usedWords: MutableSet<String> = mutableSetOf()
    private lateinit var currentWord: String

    init {
        resetGame()
    }

    /**
     * Re-initializes the game data to restart the game.
     */
    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    /**
     * Update the user's guess
     */
    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        // Scramble the word
        tempWord.shuffle()
        while (String(tempWord).equals(word)) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    private fun pickRandomWordAndShuffle(): String {
        // Continue picking up a new random word until you get one that hasn't been used before
        currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }
}