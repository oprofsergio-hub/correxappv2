package com.correxapp.data.repository

import com.correxapp.data.database.dao.ExamDao
import com.correxapp.data.mapper.toDomain
import com.correxapp.data.mapper.toEntity
import com.correxapp.domain.model.Exam
import com.correxapp.domain.repository.ExamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExamRepositoryImpl @Inject constructor(
    private val examDao: ExamDao
) : ExamRepository {

    override fun getAllExams(): Flow<List<Exam>> = 
        examDao.getAllExams().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getExamById(id: Long): Exam? = 
        examDao.getExamById(id)?.toDomain()

    override suspend fun insertExam(exam: Exam): Long = 
        examDao.insertExam(exam.toEntity())

    override suspend fun updateExam(exam: Exam) = 
        examDao.updateExam(exam.toEntity())

    override suspend fun deleteExam(exam: Exam) = 
        examDao.deleteExam(exam.toEntity())

    override fun getExamCount(): Flow<Int> = 
        examDao.getExamCount()
}
