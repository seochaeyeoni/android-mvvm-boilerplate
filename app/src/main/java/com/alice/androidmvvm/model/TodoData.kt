package com.alice.androidmvvm.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity
@Parcelize
data class TodoData(
    @PrimaryKey val date: Int,
    val title: String,
    val checkList: @RawValue List<Check>
) : Parcelable

@Parcelize
data class Check(
    val text: String = "",
    val isChecked: Boolean = false
) : Parcelable
