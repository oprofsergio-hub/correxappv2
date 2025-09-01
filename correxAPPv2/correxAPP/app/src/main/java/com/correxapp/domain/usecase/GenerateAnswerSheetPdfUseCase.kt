package com.correxapp.domain.usecase

import android.net.Uri
import com.correxapp.core.pdf.AnswerSheetPdfGenerator
import com.correxapp.domain.repository.ExamRepository
import javax.inject.Inject

class GenerateAnswerSheetPdfUseCase @Inject constructor(
    private val examRepository: ExamRepository,
    private val pdfGenerator: AnswerSheetPdfGenerator
) {
    suspend operator fun invoke(examId: Long): Result<Uri> {
        return try {
            val exam = examRepository.getExamById(examId)
                ?: return Result.failure(Exception("Prova não encontrada."))

            val pdfUri = pdfGenerator.generatePdf(
                examId = exam.id.toString(),
                examName = exam.name,
                studentNameField = true,
                totalQuestions = exam.totalQuestions,
                alternatives = exam.alternativesPerQuestion
            )
            Result.success(pdfUri)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
