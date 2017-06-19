package com.example.alfonslange.nittettest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView
import kotlinx.android.synthetic.main.swipeable_vote_image.view.*

/**
 * TODO: document your custom view class.
 */
class SwipeableVoteImageView : ImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)


    private val TAG = "SwipeableVoteImageView"

    private var mStartDragPos: Float = 0f

    private var mOnSwipeableImageEventsListener: OnSwipeableImageEventsListener? = null
    var onSwipeableImageEventsListener: OnSwipeableImageEventsListener?
        get() = this.mOnSwipeableImageEventsListener
        set(value) {
            mOnSwipeableImageEventsListener = value
        }

    private fun resizeToFitImage(bitmap: Bitmap) {
        when {
            bitmap.width > bitmap.height -> {

            }
        }
        //setMeasuredDimension()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //val width = measuredWidth
        //setMeasuredDimension(width, width)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {

                Log.d(TAG, "Drag started, pos ${event.x}, ${event.y}")
                mStartDragPos = event.y
            }

            MotionEvent.ACTION_MOVE -> {
                var diffY = (event.y - mStartDragPos) / 2

                if (diffY > 255) diffY = 255f
                if (diffY < -255) diffY = -255f

                when {
                    diffY < 0 -> {
                        setColorFilter(Color.argb(-diffY.toInt(), 0, 255, 0))
                        Log.d(TAG, "Green, Alpha ${diffY.toInt()}")
                    }
                    diffY > 0 -> {
                        setColorFilter(Color.argb(diffY.toInt(), 255, 0, 0))
                        Log.d(TAG, "Red, Alpha ${-diffY.toInt()}")
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                val diffY = (event.y - mStartDragPos) / 2

                when {
                    diffY > 255 -> {
                        mOnSwipeableImageEventsListener!!.onSwipeDown()
                    }
                    diffY < -255 -> {
                        mOnSwipeableImageEventsListener!!.onSwipeUp()
                    }
                    diffY in -10..10 -> {
                        setColorFilter(Color.argb(0, 0, 0, 0))
                        mOnSwipeableImageEventsListener!!.onClick()
                    }
                    else -> {
                        setColorFilter(Color.argb(128, 180, 0, 180))
                    }
                }
            }
        }
        return true
        //return super.onTouchEvent(event)
    }

    interface OnSwipeableImageEventsListener {
        fun onSwipeUp()
        fun onSwipeDown()
        fun onClick()
    }

    init {
    }
}
