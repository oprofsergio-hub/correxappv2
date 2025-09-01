package com.correxapp.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class StudentResponseWithAnswers(
    @Embedded val studentResponse: StudentResponseEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "response_id"
    )
    val individualAnswers: List<IndividualAnswerEntity>
)
