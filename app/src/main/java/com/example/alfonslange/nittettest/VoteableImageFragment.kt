package com.example.alfonslange.nittettest

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.fragment_voteable_image.*
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.imageBitmap


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [VoteableImageFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [VoteableImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VoteableImageFragment : Fragment() {

    private lateinit var mImagePath: String

    private var mCurrentVotingIndex = 0

    private lateinit var mLowScore: List<String>

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        doAsync {
            mImagePath.httpGet().responseString{request, response, result ->
                when(result){
                    is Result.Success -> votableImageView.imageBitmap = BitmapFactory.decodeByteArray(response.data, 0, response.data.size)
                    is Result.Failure -> votableImageView.imageBitmap = null
                }
            }
        }

        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_voteable_image, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
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

        private val IMAGE_PATH_KEY = "IMAGE_PATH_KEY"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param param1 Parameter 1.
         * *
         * @param param2 Parameter 2.
         * *
         * @return A new instance of fragment VoteableImageFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(imagePath: String): VoteableImageFragment {
            val fragment = VoteableImageFragment()
            val args = Bundle()
            args.putString(IMAGE_PATH_KEY, imagePath)
            fragment.arguments = args
            return fragment
        }
    }
}
