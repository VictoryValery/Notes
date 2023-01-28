package com.victoryvalery.noteapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victoryvalery.noteapp.feature_note.domain.model.Note
import com.victoryvalery.noteapp.feature_note.domain.use_case.NoteUseCases
import com.victoryvalery.noteapp.feature_note.domain.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val useCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var lastDeleteNote: Note? = null
    private var getNotesJob: Job? = null

    init {
        getNotes(_state.value.noteOrder)
    }

    fun catchAction(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (_state.value.noteOrder::class == event.noteOrder::class &&
                    _state.value.noteOrder.orderType == event.noteOrder.orderType
                )
                    return
                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    useCases.deleteNoteUseCase(event.note)
                    lastDeleteNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    useCases.addNoteUseCase(lastDeleteNote ?: return@launch)
                    lastDeleteNote = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !_state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = useCases.getNotesUseCase(noteOrder).onEach { notes ->
            _state.value = _state.value.copy(
                notes = notes,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }

}