package com.correxapp.data.database.dao

import androidx.room.*
import com.correxapp.data.database.entity.IndividualAnswerEntity
import com.correxapp.data.database.entity.StudentResponseEntity
import com.correxapp.data.database.entity.StudentResponseWithAnswers
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentResponseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudentResponse(response: StudentResponseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIndividualAnswers(answers: List<IndividualAnswerEntity>)

    @Transaction
    suspend fun insertResponseWithAnswers(
        response: StudentResponseEntity,
        answers: List<IndividualAnswerEntity>
    ) {
        val responseId = insertStudentResponse(response)
        val answersWithResponseId = answers.map { it.copy(responseId = responseId) }
        insertIndividualAnswers(answersWithResponseId)
    }

    @Transaction
    @Query("SELECT * FROM student_responses WHERE id = :responseId")
    suspend fun getStudentResponseWithAnswers(responseId: Long): StudentResponseWithAnswers?

    @Transaction
    @Query("SELECT * FROM student_responses WHERE exam_id = :examId ORDER BY correction_date DESC")
    fun getResponsesForExam(examId: Long): Flow<List<StudentResponseWithAnswers>>

    @Query("SELECT COUNT(*) FROM student_responses")
    fun getTotalCorrectedSheetsCount(): Flow<Int>

    @Query("SELECT AVG(score) FROM student_responses")
    fun getAverageScore(): Flow<Double?>

    @Transaction
    @Query("SELECT * FROM student_responses ORDER BY correction_date DESC LIMIT 5")
    fun getLatestResponsesWithAnswers(): Flow<List<StudentResponseWithAnswers>>
}
