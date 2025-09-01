package com.correxapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "individual_answers",
    foreignKeys = [
        ForeignKey(
            entity = StudentResponseEntity::class,
            parentColumns = ["id"],
            childColumns = ["response_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("response_id")]
)
data class IndividualAnswerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "response_id") val responseId: Long,
    @ColumnInfo(name = "question_number") val questionNumber: Int,
    @ColumnInfo(name = "marked_alternative") val markedAlternative: Int,
    @ColumnInfo(name = "is_correct") val isCorrect: Boolean,
    val confidence: Float,
    @ColumnInfo(name = "is_ambiguous") val isAmbiguous: Boolean
)
