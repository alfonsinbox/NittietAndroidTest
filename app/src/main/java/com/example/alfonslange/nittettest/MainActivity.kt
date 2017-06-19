package com.example.alfonslange.nittettest

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.result.Result
import java.io.File


class MainActivity : AppCompatActivity(), PictureTakenCallback, NineImagesVoteThreeFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFullscreen()

        /*if (null == savedInstanceState) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, CameraFragment.newInstance())
                    .commit()
        }*/
        if (null == savedInstanceState) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, NineImagesVoteThreeFragment.newInstance())
                    .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        setFullscreen()
    }

    fun setFullscreen() {
        window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }

    override fun pictureTaken(file: File) {
        Log.d(TAG, "Callback triggered!")
        FuelManager.instance.basePath = "http://192.168.10.9:5000"
        Fuel.upload("/api/image/upload").source { request, url ->
            file
        }.responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    Log.e(TAG, response.toString())
                }
                is Result.Success -> {
                    Log.d(TAG, response.toString())
                }
            }
        }
    }
}
