package com.correxapp.core.omr

import android.graphics.Bitmap
import com.correxapp.domain.model.IndividualAnswer
import com.correxapp.domain.model.OmrResult
import javax.inject.Inject

class OmrProcessor @Inject constructor() {

    companion object {
        private const val MARKING_THRESHOLD_RATIO = 0.4f
    }

    fun processSheet(
        bitmap: Bitmap,
        totalQuestions: Int,
        alternativesPerQuestion: Int,
        answerKey: Map<Int, Int>
    ): OmrResult {
        val startTime = System.currentTimeMillis()

        try {
            val individualAnswers = mutableListOf<IndividualAnswer>()
            var correctCount = 0

            // Simulação de processamento OMR
            for (qNum in 1..totalQuestions) {
                val markedAlternative = (0 until alternativesPerQuestion).random()
                val correctAnswer = answerKey[qNum] ?: -1
                val isCorrect = markedAlternative == correctAnswer
                val confidence = 0.8f + (Math.random() * 0.2f).toFloat()

                if (isCorrect) correctCount++

                individualAnswers.add(
                    IndividualAnswer(
                        questionNumber = qNum,
                        markedAlternative = markedAlternative,
                        isCorrect = isCorrect,
                        isAmbiguous = false,
                        confidence = confidence,
                        id = 0,
                        responseId = 0
                    )
                )
            }

            val score = if (totalQuestions > 0) {
                (correctCount.toDouble() / totalQuestions) * 100.0
            } else 0.0

            val processingTime = System.currentTimeMillis() - startTime
            val averageConfidence = individualAnswers.map { it.confidence }.average().toFloat()

            return OmrResult.Success(
                studentName = "",
                score = score,
                hits = correctCount,
                processingTimeMs = processingTime,
                averageConfidence = averageConfidence,
                individualAnswers = individualAnswers
            )

        } catch (e: Exception) {
            return OmrResult.Error("Erro no processamento: " + e.message)
        }
    }
}
