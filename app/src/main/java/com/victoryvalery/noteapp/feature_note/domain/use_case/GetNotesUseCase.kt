package com.victoryvalery.noteapp.feature_note.domain.use_case

import com.victoryvalery.noteapp.feature_note.domain.model.Note
import com.victoryvalery.noteapp.feature_note.domain.repository.NoteRepository
import com.victoryvalery.noteapp.feature_note.domain.util.NoteOrder
import com.victoryvalery.noteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val repository: NoteRepository
) {

    operator fun invoke(noteOrder: NoteOrder): Flow<List<Note>> {

        return repository.getNotes().map {
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> it.sortedBy { note -> note.title.lowercase() }
                        is NoteOrder.Date -> it.sortedBy { note -> note.timestamp }
                        is NoteOrder.Color -> it.sortedBy { note -> note.color }
                    }
                }
                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> it.sortedByDescending { note -> note.title }
                        is NoteOrder.Date -> it.sortedByDescending { note -> note.timestamp }
                        is NoteOrder.Color -> it.sortedByDescending { note -> note.color }
                    }
                }
            }
        }

    }

}