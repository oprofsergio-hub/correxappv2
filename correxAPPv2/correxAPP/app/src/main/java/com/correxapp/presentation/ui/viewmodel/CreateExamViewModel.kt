package com.correxapp.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.correxapp.domain.model.Exam
import com.correxapp.domain.usecase.CreateExamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateExamUiState(
    val name: String = "",
    val subject: String = "",
    val totalQuestions: String = "10",
    val alternativesPerQuestion: String = "4",
    val answerKey: Map<Int, Int> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val examCreatedSuccessfully: Boolean = false
)

@HiltViewModel
class CreateExamViewModel @Inject constructor(
    private val createExamUseCase: CreateExamUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateExamUiState())
    val uiState = _uiState.asStateFlow()

    init {
        updateAnswerKeyBasedOnConfig()
    }

    fun onNameChange(newName: String) = _uiState.update { it.copy(name = newName) }

    fun onSubjectChange(newSubject: String) = _uiState.update { it.copy(subject = newSubject) }

    fun onTotalQuestionsChange(newTotal: String) {
        if (newTotal.all { it.isDigit() }) {
            _uiState.update { it.copy(totalQuestions = newTotal) }
            updateAnswerKeyBasedOnConfig()
        }
    }

    fun onAlternativesChange(newAlternatives: String) {
        if (newAlternatives.all { it.isDigit() }) {
            _uiState.update { it.copy(alternativesPerQuestion = newAlternatives) }
            updateAnswerKeyBasedOnConfig()
        }
    }

    fun onAnswerChange(questionNumber: Int, alternativeIndex: Int) = 
        _uiState.update { 
            it.copy(answerKey = it.answerKey.toMutableMap().apply { 
                this[questionNumber] = alternativeIndex 
            }) 
        }

    private fun updateAnswerKeyBasedOnConfig() = _uiState.update { 
        it.copy(answerKey = (1..(it.totalQuestions.toIntOrNull() ?: 0)).associateWith { -1 }) 
    }

    fun saveExam() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val state = _uiState.value
            if (state.answerKey.any { it.value == -1 }) {
                _uiState.update { 
                    it.copy(isLoading = false, error = "Por favor, preencha todo o gabarito.") 
                }
                return@launch
            }

            val exam = Exam(
                name = state.name.trim(),
                subject = state.subject.trim(),
                totalQuestions = state.totalQuestions.toIntOrNull() ?: 0,
                alternativesPerQuestion = state.alternativesPerQuestion.toIntOrNull() ?: 0,
                creationDate = System.currentTimeMillis(),
                answerKey = state.answerKey
            )

            createExamUseCase(exam)
                .onSuccess { _uiState.update { it.copy(isLoading = false, examCreatedSuccessfully = true) } }
                .onFailure { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
        }
    }

    fun dismissError() = _uiState.update { it.copy(error = null) }
}
