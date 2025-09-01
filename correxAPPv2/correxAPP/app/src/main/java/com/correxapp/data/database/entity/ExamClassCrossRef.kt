package com.correxapp.data.database.entity

import androidx.room.Entity

@Entity(primaryKeys = ["examId", "classId"])
data class ExamClassCrossRef(
    val examId: Long,
    val classId: Long
)