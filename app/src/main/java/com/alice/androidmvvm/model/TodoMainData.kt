package com.alice.androidmvvm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoMainData(
    @PrimaryKey val date: Int,
    val title: String
)
