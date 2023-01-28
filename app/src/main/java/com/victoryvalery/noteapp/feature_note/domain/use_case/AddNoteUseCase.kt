package com.victoryvalery.noteapp.feature_note.domain.use_case

import com.victoryvalery.noteapp.feature_note.domain.model.InvalidNoteException
import com.victoryvalery.noteapp.feature_note.domain.model.Note
import com.victoryvalery.noteapp.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Please, add title")
        }
        repository.insertNote(note)
    }
}