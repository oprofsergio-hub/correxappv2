package com.correxapp.domain.model

sealed class OmrResult {
    data class Success(
        val studentName: String,
        val score: Double,
        val hits: Int,
        val processingTimeMs: Long,
        val averageConfidence: Float,
        val individualAnswers: List<IndividualAnswer>
    ) : OmrResult()

    data class Error(val message: String) : OmrResult()
}
