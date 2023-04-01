package com.test.a5.di

import com.test.a5.domain.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainObject {

    @Singleton
    @Provides
    fun provideNetworkUseCase(networkRepository: NetworkRepository) =
        NetworkUseCase(networkRepository)

    @Singleton
    @Provides
    fun providePreferencesUseCase(preferencesRepository: PreferencesRepository) =
        PreferencesUseCase(preferencesRepository)


}