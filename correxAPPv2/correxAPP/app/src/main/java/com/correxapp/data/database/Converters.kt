package com.correxapp.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromMap(value: Map<Int, Int>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toMap(value: String): Map<Int, Int> {
        return Json.decodeFromString(value)
    }
}
