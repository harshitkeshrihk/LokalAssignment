package com.example.lokalassignmentapp.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lokalassignmentapp.model.Result

@Database(entities = [Result::class], version = 1, exportSchema = false)
@TypeConverters(Convertors::class)
abstract class BookMarkDB: RoomDatabase() {
    abstract val dao: BookMarkDao
}