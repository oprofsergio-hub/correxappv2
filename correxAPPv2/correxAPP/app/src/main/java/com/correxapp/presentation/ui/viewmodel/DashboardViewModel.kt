package com.correxapp.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.correxapp.domain.model.DashboardStats
import com.correxapp.domain.model.Exam
import com.correxapp.domain.usecase.GetAllExamsUseCase
import com.correxapp.domain.usecase.GetDashboardStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    getDashboardStatsUseCase: GetDashboardStatsUseCase,
    getAllExamsUseCase: GetAllExamsUseCase
) : ViewModel() {

    val dashboardStats: StateFlow<DashboardStats> = getDashboardStatsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardStats(0, 0, 0.0, emptyList())
    )

    val allExams: StateFlow<List<Exam>> = getAllExamsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
}
