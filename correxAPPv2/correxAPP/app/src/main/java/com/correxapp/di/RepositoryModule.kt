package com.correxapp.di

import com.correxapp.data.repository.ExamRepositoryImpl
import com.correxapp.data.repository.StudentResponseRepositoryImpl
import com.correxapp.domain.repository.ExamRepository
import com.correxapp.domain.repository.StudentResponseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindExamRepository(impl: ExamRepositoryImpl): ExamRepository

    @Binds
    @Singleton
    abstract fun bindStudentResponseRepository(impl: StudentResponseRepositoryImpl): StudentResponseRepository
}
