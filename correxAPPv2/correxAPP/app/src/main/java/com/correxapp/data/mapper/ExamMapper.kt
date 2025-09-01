package com.correxapp.data.mapper

import com.correxapp.data.database.entity.ExamEntity
import com.correxapp.domain.model.Exam
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun ExamEntity.toDomain(): Exam {
    return Exam(
        id = this.id,
        name = this.name,
        subject = this.subject,
        totalQuestions = this.totalQuestions,
        alternativesPerQuestion = this.alternativesPerQuestion,
        creationDate = this.creationDate,
        answerKey = Json.decodeFromString(this.answerKeyJson)
    )
}

fun Exam.toEntity(): ExamEntity {
    return ExamEntity(
        id = this.id,
        name = this.name,
        subject = this.subject,
        totalQuestions = this.totalQuestions,
        alternativesPerQuestion = this.alternativesPerQuestion,
        creationDate = this.creationDate,
        answerKeyJson = Json.encodeToString(this.answerKey)
    )
}
