package com.correxapp.domain.model

data class DashboardStats(
    val totalExams: Int,
    val totalCorrectedSheets: Int,
    val averageScore: Double,
    val latestResponses: List<StudentResponse>
)
