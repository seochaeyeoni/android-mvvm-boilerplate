package com.alice.androidmvvm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoData(
    @PrimaryKey val dateTime: Long,
    val isDone: Boolean = false,
    val text: String = ""
)
