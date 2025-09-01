package com.correxapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "student_responses",
    foreignKeys = [
        ForeignKey(
            entity = ExamEntity::class,
            parentColumns = ["id"],
            childColumns = ["exam_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("exam_id")]
)
data class StudentResponseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "exam_id") val examId: Long,
    @ColumnInfo(name = "student_name") val studentName: String,
    @ColumnInfo(name = "correction_date") val correctionDate: Long,
    val score: Double,
    val hits: Int,
    @ColumnInfo(name = "processing_time_ms") val processingTimeMs: Long,
    @ColumnInfo(name = "average_confidence") val averageConfidence: Float
)
