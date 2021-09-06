package com.pipi.pipix.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PureResultDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPureResult(pr: PureResult)

    @Query("SELECT * FROM pure_result_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<PureResult>>
}