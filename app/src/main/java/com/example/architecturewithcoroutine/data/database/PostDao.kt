package com.example.architecturewithcoroutine.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.architecturewithcoroutine.data.models.Post

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(dataList:List<Post>)

    @Query("Select * from Post")
    fun getAllData():LiveData<List<Post>>
}