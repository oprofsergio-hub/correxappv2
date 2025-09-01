package com.correxapp.di

import com.correxapp.core.omr.OmrProcessor
import com.correxapp.core.utils.QrCodeGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOmrProcessor(): OmrProcessor = OmrProcessor()

    @Provides
    @Singleton
    fun provideQrCodeGenerator(): QrCodeGenerator = QrCodeGenerator()
}
