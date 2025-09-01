package com.correxapp.domain.repository

import com.correxapp.domain.model.StudentResponse
import kotlinx.coroutines.flow.Flow

interface StudentResponseRepository {
    suspend fun insertStudentResponse(response: StudentResponse)
    suspend fun getStudentResponseWithAnswers(responseId: Long): StudentResponse?
    fun getResponsesForExam(examId: Long): Flow<List<StudentResponse>>
    fun getTotalCorrectedSheetsCount(): Flow<Int>
    fun getAverageScore(): Flow<Double?>
    fun getLatestResponses(): Flow<List<StudentResponse>>
}
