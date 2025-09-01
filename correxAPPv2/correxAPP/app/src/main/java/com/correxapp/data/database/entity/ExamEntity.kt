package com.correxapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exams")
data class ExamEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val subject: String,
    @ColumnInfo(name = "total_questions") val totalQuestions: Int,
    @ColumnInfo(name = "alternatives_per_question") val alternativesPerQuestion: Int,
    @ColumnInfo(name = "creation_date") val creationDate: Long,
    @ColumnInfo(name = "answer_key_json") val answerKeyJson: String
)
