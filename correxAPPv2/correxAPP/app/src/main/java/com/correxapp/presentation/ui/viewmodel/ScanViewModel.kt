package com.correxapp.presentation.ui.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.correxapp.domain.model.StudentResponse
import com.correxapp.domain.usecase.GradeSheetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ScanUiState(
    val isProcessing: Boolean = false,
    val result: StudentResponse? = null,
    val error: String? = null,
    val instruction: String = "Aponte para o cartão-resposta"
)

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val gradeSheetUseCase: GradeSheetUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val examId: Long = checkNotNull(savedStateHandle["examId"])
    private var processingJob: Job? = null

    private val _uiState = MutableStateFlow(ScanUiState())
    val uiState = _uiState.asStateFlow()

    fun onImageCaptured(bitmap: Bitmap) {
        if (processingJob?.isActive == true) return

        processingJob = viewModelScope.launch {
            _uiState.update { it.copy(isProcessing = true, instruction = "Processando...") }

            gradeSheetUseCase(examId = examId, studentName = "Aluno", sheetBitmap = bitmap)
                .onSuccess { studentResponse ->
                    _uiState.update { 
                        it.copy(isProcessing = false, result = studentResponse, instruction = "Correção concluída!") 
                    }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(isProcessing = false, error = error.message, instruction = "Tente novamente") 
                    }
                    delay(2000)
                    _uiState.update { it.copy(error = null) }
                }
        }
    }

    fun resetScan() {
        _uiState.update { 
            ScanUiState(instruction = "Aponte para o cartão-resposta") 
        }
    }
}
