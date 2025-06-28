package com.jr2081.jianrong34693807.data

sealed interface UiState {

    object Initial : UiState

    object Loading : UiState

    data class Success(val outputText: String) : UiState

    data class Error(val errorMessage: String) : UiState
}