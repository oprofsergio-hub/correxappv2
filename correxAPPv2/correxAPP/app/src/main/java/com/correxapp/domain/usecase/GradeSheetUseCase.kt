package com.correxapp.domain.usecase

import android.graphics.Bitmap
import com.correxapp.core.omr.OmrProcessor
import com.correxapp.domain.model.OmrResult
import com.correxapp.domain.model.StudentResponse
import com.correxapp.domain.repository.ExamRepository
import com.correxapp.domain.repository.StudentResponseRepository
import javax.inject.Inject

class GradeSheetUseCase @Inject constructor(
    private val examRepository: ExamRepository,
    private val studentResponseRepository: StudentResponseRepository,
    private val omrProcessor: OmrProcessor
) {
    suspend operator fun invoke(
        examId: Long,
        studentName: String,
        sheetBitmap: Bitmap
    ): Result<StudentResponse> {
        return try {
            val exam = examRepository.getExamById(examId)
                ?: return Result.failure(Exception("Prova com ID " + examId + " não encontrada."))

            val omrResult = omrProcessor.processSheet(
                bitmap = sheetBitmap,
                totalQuestions = exam.totalQuestions,
                alternativesPerQuestion = exam.alternativesPerQuestion,
                answerKey = exam.answerKey
            )

            when (omrResult) {
                is OmrResult.Success -> {
                    val studentResponse = StudentResponse(
                        examId = examId,
                        studentName = studentName.ifBlank { "Aluno anônimo" },
                        correctionDate = System.currentTimeMillis(),
                        score = omrResult.score,
                        hits = omrResult.hits,
                        processingTimeMs = omrResult.processingTimeMs,
                        averageConfidence = omrResult.averageConfidence,
                        individualAnswers = omrResult.individualAnswers
                    )
                    studentResponseRepository.insertStudentResponse(studentResponse)
                    Result.success(studentResponse)
                }
                is OmrResult.Error -> {
                    Result.failure(Exception(omrResult.message))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
