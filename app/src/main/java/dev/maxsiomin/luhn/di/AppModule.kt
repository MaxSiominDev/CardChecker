package dev.maxsiomin.luhn.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.maxsiomin.luhn.util.UiActions
import dev.maxsiomin.luhn.util.UiActionsImpl
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
