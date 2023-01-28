package com.victoryvalery.noteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.victoryvalery.noteapp.ui.theme.*

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey
    val id: Int? = null
) {
    companion object {
        val noteColors = listOf(
            SandWhite,
            RedOrange,
            Violet,
            LightGreen,
            BabyBlue,
            Mustard
        )
    }
}

class InvalidNoteException(message: String) : Exception(message)