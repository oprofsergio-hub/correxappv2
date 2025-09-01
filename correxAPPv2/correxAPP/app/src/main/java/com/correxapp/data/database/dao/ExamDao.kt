package com.correxapp.data.database.dao

import androidx.room.*
import com.correxapp.data.database.entity.ExamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExam(exam: ExamEntity): Long

    @Update
    suspend fun updateExam(exam: ExamEntity)

    @Delete
    suspend fun deleteExam(exam: ExamEntity)

    @Query("SELECT * FROM exams WHERE id = :examId")
    suspend fun getExamById(examId: Long): ExamEntity?

    @Query("SELECT * FROM exams ORDER BY creation_date DESC")
    fun getAllExams(): Flow<List<ExamEntity>>

    @Query("SELECT COUNT(*) FROM exams")
    fun getExamCount(): Flow<Int>
}
