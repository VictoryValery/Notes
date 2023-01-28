package com.victoryvalery.noteapp.feature_note.data.data_sorce

import androidx.room.Database
import androidx.room.RoomDatabase
import com.victoryvalery.noteapp.feature_note.domain.model.Note

@Database(
    [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notesDB"
    }

}