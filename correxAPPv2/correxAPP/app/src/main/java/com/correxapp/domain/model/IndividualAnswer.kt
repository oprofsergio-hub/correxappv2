package com.correxapp.domain.model

data class IndividualAnswer(
    val id: Long = 0,
    val responseId: Long,
    val questionNumber: Int,
    val markedAlternative: Int,
    val isCorrect: Boolean,
    val confidence: Float,
    val isAmbiguous: Boolean
)
