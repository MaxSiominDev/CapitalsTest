package dev.maxsiomin.capitals.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.maxsiomin.capitals.util.UiActions
import dev.maxsiomin.capitals.util.UiActionsImpl
import javax.inject.Singleton

/**
 * AppModule for DI
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUiActions(@ApplicationContext context: Context): UiActions = UiActionsImpl(context)
}


