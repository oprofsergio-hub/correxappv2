package com.correxapp.domain.usecase

import com.correxapp.domain.model.Exam
import com.correxapp.domain.repository.ExamRepository
import javax.inject.Inject

class CreateExamUseCase @Inject constructor(
    private val examRepository: ExamRepository
) {
    suspend operator fun invoke(exam: Exam): Result<Long> {
        return try {
            if (exam.name.isBlank() || exam.subject.isBlank()) {
                throw IllegalArgumentException("Nome e disciplina não podem ser vazios.")
            }
            if (exam.totalQuestions !in 1..200) {
                throw IllegalArgumentException("O número de questões deve ser entre 1 e 200.")
            }
            if (exam.alternativesPerQuestion !in 2..5) {
                throw IllegalArgumentException("O número de alternativas deve ser entre 2 e 5.")
            }
            val id = examRepository.insertExam(exam)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
