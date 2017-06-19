package com.example.alfonslange.nittettest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

/**
 * Created by alfonslange on 2017-06-16.
 */
fun bitmapFromUrl(url: String): Bitmap? {
    var loadedBitmap: Bitmap? = null

    url.httpGet().responseString { request, response, result ->
        when (result) {
            is Result.Success -> {
                loadedBitmap = BitmapFactory.decodeByteArray(response.data, 0, response.data.size)
            }
        }
    }

    return loadedBitmap
}