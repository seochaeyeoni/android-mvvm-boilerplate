package com.alice.androidmvvm.util

import androidx.room.TypeConverter
import com.alice.androidmvvm.model.Check
import com.google.gson.Gson

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun checkListToJson(value: MutableList<Check>) = gson.toJson(value)

    @TypeConverter
    fun jsonToCheckList(value: String) = gson.fromJson(value, Array<Check>::class.java).toMutableList()
}
