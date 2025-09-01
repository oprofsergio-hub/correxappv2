package com.correxapp.di

import android.content.Context
import androidx.room.Room
import com.correxapp.data.database.AppDatabase
import com.correxapp.data.database.dao.ClassDao
import com.correxapp.data.database.dao.ExamDao
import com.correxapp.data.database.dao.StudentDao
import com.correxapp.data.database.dao.StudentResponseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration() // Facilita o desenvolvimento ao recriar o DB em mudanças de versão
            .build()
    }

    @Provides
    fun provideExamDao(appDatabase: AppDatabase): ExamDao = appDatabase.examDao()

    @Provides
    fun provideStudentResponseDao(appDatabase: AppDatabase): StudentResponseDao = appDatabase.studentResponseDao()

    @Provides
    fun provideClassDao(appDatabase: AppDatabase): ClassDao = appDatabase.classDao()

    @Provides
    fun provideStudentDao(appDatabase: AppDatabase): StudentDao = appDatabase.studentDao()
}