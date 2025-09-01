package com.correxapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.correxapp.data.database.dao.*
import com.correxapp.data.database.entity.*

@Database(
    entities = [
        ExamEntity::class,
        StudentResponseEntity::class,
        ClassEntity::class,
        StudentEntity::class,
        IndividualAnswerEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun examDao(): ExamDao
    abstract fun studentResponseDao(): StudentResponseDao
    abstract fun classDao(): ClassDao
    abstract fun studentDao(): StudentDao

    companion object {
        const val DATABASE_NAME = "correx_app_db"
    }
}