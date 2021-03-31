package com.example.chuibbo_android.image

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ImageDao {
    @Query("SELECT * FROM Image_table")
    fun getAllImage(): LiveData<List<Image>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertImage(image: Image)

    @Query("Delete from Image_table")
    suspend fun deleteAll()
}