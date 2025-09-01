package com.correxapp.domain.usecase

import com.correxapp.domain.model.DashboardStats
import com.correxapp.domain.repository.ExamRepository
import com.correxapp.domain.repository.StudentResponseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetDashboardStatsUseCase @Inject constructor(
    private val examRepository: ExamRepository,
    private val studentResponseRepository: StudentResponseRepository
) {
    operator fun invoke(): Flow<DashboardStats> {
        return combine(
            examRepository.getExamCount(),
            studentResponseRepository.getTotalCorrectedSheetsCount(),
            studentResponseRepository.getAverageScore(),
            studentResponseRepository.getLatestResponses()
        ) { totalExams, totalSheets, avgScore, latestResponses ->
            DashboardStats(
                totalExams = totalExams,
                totalCorrectedSheets = totalSheets,
                averageScore = avgScore ?: 0.0,
                latestResponses = latestResponses
            )
        }
    }
}
