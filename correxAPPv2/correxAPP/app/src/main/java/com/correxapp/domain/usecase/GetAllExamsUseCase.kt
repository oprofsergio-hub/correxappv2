package com.correxapp.domain.usecase

import com.correxapp.domain.model.Exam
import com.correxapp.domain.repository.ExamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllExamsUseCase @Inject constructor(
    private val examRepository: ExamRepository
) {
    operator fun invoke(): Flow<List<Exam>> {
        return examRepository.getAllExams()
    }
}
