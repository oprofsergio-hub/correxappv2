package com.correxapp.domain.model

data class StudentResponse(
    val id: Long = 0,
    val examId: Long,
    val studentName: String,
    val correctionDate: Long,
    val score: Double,
    val hits: Int,
    val processingTimeMs: Long,
    val averageConfidence: Float,
    val individualAnswers: List<IndividualAnswer>
)
