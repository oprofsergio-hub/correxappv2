package com.correxapp.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ClassWithStudents(
    @Embedded val classEntity: ClassEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "class_id"
    )
    val students: List<StudentEntity>
)