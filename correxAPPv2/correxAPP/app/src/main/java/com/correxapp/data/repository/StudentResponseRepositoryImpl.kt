package com.correxapp.data.repository

import com.correxapp.data.database.dao.StudentResponseDao
import com.correxapp.data.mapper.toDomain
import com.correxapp.data.mapper.toEntityWithAnswers
import com.correxapp.domain.model.StudentResponse
import com.correxapp.domain.repository.StudentResponseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StudentResponseRepositoryImpl @Inject constructor(
    private val studentResponseDao: StudentResponseDao
) : StudentResponseRepository {

    override suspend fun insertStudentResponse(response: StudentResponse) {
        val (responseEntity, answersEntity) = response.toEntityWithAnswers()
        studentResponseDao.insertResponseWithAnswers(responseEntity, answersEntity)
    }

    override suspend fun getStudentResponseWithAnswers(responseId: Long): StudentResponse? = 
        studentResponseDao.getStudentResponseWithAnswers(responseId)?.toDomain()

    override fun getResponsesForExam(examId: Long): Flow<List<StudentResponse>> = 
        studentResponseDao.getResponsesForExam(examId).map { list -> list.map { it.toDomain() } }

    override fun getTotalCorrectedSheetsCount(): Flow<Int> = 
        studentResponseDao.getTotalCorrectedSheetsCount()

    override fun getAverageScore(): Flow<Double?> = 
        studentResponseDao.getAverageScore()

    override fun getLatestResponses(): Flow<List<StudentResponse>> = 
        studentResponseDao.getLatestResponsesWithAnswers().map { list -> list.map { it.toDomain() } }
}
