package com.example.lokalassignmentapp.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lokalassignmentapp.model.Result

@Dao
interface BookMarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: Result)

    @Delete
    suspend fun deleteJob(job: Result)

    @Query("SELECT * FROM jobtable")
    suspend fun getAllBookmarks(): List<Result>

}