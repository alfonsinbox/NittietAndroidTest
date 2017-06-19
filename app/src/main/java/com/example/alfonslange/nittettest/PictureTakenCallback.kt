package com.example.alfonslange.nittettest

import java.io.File

/**
 * Created by alfonslange on 2017-05-28.
 */
interface PictureTakenCallback {
    fun pictureTaken(file: File)
}