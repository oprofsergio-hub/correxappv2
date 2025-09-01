package com.correxapp.data.mapper

import com.correxapp.data.database.entity.IndividualAnswerEntity
import com.correxapp.data.database.entity.StudentResponseEntity
import com.correxapp.data.database.entity.StudentResponseWithAnswers
import com.correxapp.domain.model.IndividualAnswer
import com.correxapp.domain.model.StudentResponse

fun StudentResponseWithAnswers.toDomain(): StudentResponse {
    return StudentResponse(
        id = this.studentResponse.id,
        examId = this.studentResponse.examId,
        studentName = this.studentResponse.studentName,
        correctionDate = this.studentResponse.correctionDate,
        score = this.studentResponse.score,
        hits = this.studentResponse.hits,
        processingTimeMs = this.studentResponse.processingTimeMs,
        averageConfidence = this.studentResponse.averageConfidence,
        individualAnswers = this.individualAnswers.map { it.toDomain() }
    )
}

fun IndividualAnswerEntity.toDomain(): IndividualAnswer {
    return IndividualAnswer(
        id = this.id,
        responseId = this.responseId,
        questionNumber = this.questionNumber,
        markedAlternative = this.markedAlternative,
        isCorrect = this.isCorrect,
        confidence = this.confidence,
        isAmbiguous = this.isAmbiguous
    )
}

fun StudentResponse.toEntityWithAnswers(): Pair<StudentResponseEntity, List<IndividualAnswerEntity>> {
    val responseEntity = StudentResponseEntity(
        id = this.id,
        examId = this.examId,
        studentName = this.studentName,
        correctionDate = this.correctionDate,
        score = this.score,
        hits = this.hits,
        processingTimeMs = this.processingTimeMs,
        averageConfidence = this.averageConfidence
    )
    val answersEntity = this.individualAnswers.map { it.toEntity() }
    return Pair(responseEntity, answersEntity)
}

fun IndividualAnswer.toEntity(): IndividualAnswerEntity {
    return IndividualAnswerEntity(
        id = this.id,
        responseId = this.responseId,
        questionNumber = this.questionNumber,
        markedAlternative = this.markedAlternative,
        isCorrect = this.isCorrect,
        confidence = this.confidence,
        isAmbiguous = this.isAmbiguous
    )
}
