package com.example.chuibbo_android.image

import androidx.lifecycle.LiveData

class ImageRepository(
    private val imageDao: ImageDao
) {
    val allimage: LiveData<List<Image>> = imageDao.getAllImage()

    suspend fun imageInsert(image: Image) {
        imageDao.insertImage(image)
    }

    suspend fun delete() {
        imageDao.deleteAll()
    }
}
