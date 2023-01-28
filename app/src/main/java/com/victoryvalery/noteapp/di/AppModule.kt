package com.victoryvalery.noteapp.di

import android.app.Application
import androidx.room.Room
import com.victoryvalery.noteapp.feature_note.data.data_sorce.NoteDatabase
import com.victoryvalery.noteapp.feature_note.data.data_sorce.NoteDatabase.Companion.DATABASE_NAME
import com.victoryvalery.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.victoryvalery.noteapp.feature_note.domain.repository.NoteRepository
import com.victoryvalery.noteapp.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideNoteRepository(noteDB: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(noteDB.noteDao)
    }

    @Singleton
    @Provides
    fun providesNoteUseCases(noteRepository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(noteRepository),
            deleteNoteUseCase = DeleteNoteUseCase(noteRepository),
            addNoteUseCase = AddNoteUseCase(noteRepository),
            getNoteUseCase = GetNoteUseCase(noteRepository)
        )
    }

}