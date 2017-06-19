package com.example.alfonslange.nittettest

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.fragment_nine_images_vote_three.*
import org.jetbrains.anko.*
import kotlin.coroutines.experimental.EmptyCoroutineContext.plus
import kotlin.properties.ObservableProperty

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [NineImagesVoteThreeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [NineImagesVoteThreeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NineImagesVoteThreeFragment : Fragment() {

    private val TAG: String = "9ImagesVote3Fragment"

    private var mListener: OnFragmentInteractionListener? = null

    private var mImageUrls = Array(9, { i -> "https://nittiet.blob.core.windows.net/image-store/IMG_2016-12-16%2009%3A41%3A06.jpg" })

    private lateinit var mVotableImages: Array<Bitmap>

    private var mStartDragPos: Float = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO Get urls to images to vote for this time

        for ((index, imageUrl) in mImageUrls.withIndex()) {
            doAsync {
                //mVotableImages[index] = bitmapFromUrl(imageUrl)!!
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_nine_images_vote_three, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context as OnFragmentInteractionListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onStart() {
        super.onStart()

        imageVoteOptionOne.setImage(mImageUrls[0])
        imageVoteOptionTwo.setImage(mImageUrls[0])
        imageVoteOptionThree.setImage(mImageUrls[0])

        val tempGenericHandler = object : SwipeableVoteImageView.OnSwipeableImageEventsListener {
            override fun onSwipeDown() {
                //Toast.makeText(ctx, "Swipe down", ).show()
            }

            override fun onSwipeUp() {
                //Toast.makeText(ctx, "Swipe up", Toast.LENGTH_SHORT).show()
            }

            override fun onClick() {
                //Toast.makeText(ctx, "CLICK!", Toast.LENGTH_SHORT).show()
            }
        }
        imageVoteOptionOne.onSwipeableImageEventsListener = tempGenericHandler
        imageVoteOptionTwo.onSwipeableImageEventsListener = tempGenericHandler
        imageVoteOptionThree.onSwipeableImageEventsListener = tempGenericHandler
    }

    private fun ImageView.setImage(url: String) {
        doAsync {
            url.httpGet().responseString { request, response, result ->
                when (result) {
                    is Result.Success -> {
                        Log.i(TAG, "Loaded image, ok?")
                        val loadedBitmap = BitmapFactory.decodeByteArray(response.data, 0, response.data.size)
                        uiThread {
                            imageBitmap = loadedBitmap
                        }
                    }
                }
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @return A new instance of fragment NineImagesVoteThreeFragment.
         */
        fun newInstance(): NineImagesVoteThreeFragment {
            /*val fragment = NineImagesVoteThreeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment*/
            return NineImagesVoteThreeFragment()
        }
    }
}// Required empty public constructor
