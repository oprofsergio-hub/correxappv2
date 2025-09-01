package com.correxapp.data.database.dao

import androidx.room.*
import com.correxapp.data.database.entity.ClassEntity
import com.correxapp.data.database.entity.ClassWithStudents
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClass(classEntity: ClassEntity)

    @Transaction
    @Query("SELECT * FROM classes ORDER BY name ASC")
    fun getClassesWithStudents(): Flow<List<ClassWithStudents>>

    @Query("SELECT * FROM classes WHERE id = :classId")
    suspend fun getClassById(classId: Long): ClassEntity?
}