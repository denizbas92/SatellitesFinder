package com.example.satellitesfinder.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Singleton
    @Provides
    @Named(ModulesConstants.ModuleName.GSon)
    fun provideGSon(): Gson = Gson()

}