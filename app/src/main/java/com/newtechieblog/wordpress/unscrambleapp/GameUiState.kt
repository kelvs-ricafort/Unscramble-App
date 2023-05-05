package com.newtechieblog.wordpress.unscrambleapp

data class GameUiState(
    val currentScrambledWord: String = "",
    val isGuessedWordWrong: Boolean = false,
)
