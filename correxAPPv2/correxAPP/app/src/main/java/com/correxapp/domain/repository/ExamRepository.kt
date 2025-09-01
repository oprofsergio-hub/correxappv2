package com.correxapp.domain.repository

import com.correxapp.domain.model.Exam
import kotlinx.coroutines.flow.Flow

interface ExamRepository {
    fun getAllExams(): Flow<List<Exam>>
    suspend fun getExamById(id: Long): Exam?
    suspend fun insertExam(exam: Exam): Long
    suspend fun updateExam(exam: Exam)
    suspend fun deleteExam(exam: Exam)
    fun getExamCount(): Flow<Int>
}
