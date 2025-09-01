package com.correxapp.domain.usecase

import com.correxapp.domain.model.Exam
import com.correxapp.domain.model.StudentResponse
import com.correxapp.domain.repository.ExamRepository
import com.correxapp.domain.repository.StudentResponseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class ExamDetails(
    val exam: Exam,
    val responses: List<StudentResponse>
)

class GetExamWithResponsesUseCase @Inject constructor(
    private val examRepository: ExamRepository,
    private val studentResponseRepository: StudentResponseRepository
) {
    operator fun invoke(examId: Long): Flow<Result<ExamDetails>> {
        val examFlow = examRepository.getAllExams().map { exams ->
            exams.find { it.id == examId }
        }
        val responsesFlow = studentResponseRepository.getResponsesForExam(examId)

        return combine(examFlow, responsesFlow) { exam, responses ->
            if (exam != null) {
                Result.success(ExamDetails(exam, responses))
            } else {
                Result.failure(Exception("Prova não encontrada."))
            }
        }
    }
}
