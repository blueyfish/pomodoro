// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro

import android.app.Activity
import android.app.TimePickerDialog
import android.gesture.Gesture
import android.os.Build
import android.os.Bundle
import android.view.GestureDetector
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import tipz.pomodoro.textart.TextArtGenerator
import tipz.pomodoro.ui.BackgroundValueAnimator
import tipz.pomodoro.ui.TimeCountingTextView
import tipz.pomodoro.ui.DurationPickerDialog
import tipz.pomodoro.ui.showWithCenteredButtons

@Suppress("DEPRECATION")
class MainActivity : Activity(),
    GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {
    private lateinit var rootView: LinearLayout
    private lateinit var timeCountingTextView: TimeCountingTextView

    private lateinit var backgroundValueAnimator: BackgroundValueAnimator
    private var completionFlashing = false
        set(value) {
            field = value
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                if (value) backgroundValueAnimator.start() else backgroundValueAnimator.stop()
            } else {
                rootView.setBackgroundColor(
                    if (value) BackgroundValueAnimator.flashCyanColor
                    else BackgroundValueAnimator.flashClearColor
                )
            }
        }

    private lateinit var detector: GestureDetector

    private fun createDurationPickerDialog() {
        val callback = { hour: Int, minute: Int, second: Int, isCountUp: Boolean ->
            if (timeCountingTextView.timerCounting) timeCountingTextView.resetCount()

            val duration = hour * 3600000L + minute * 60000L + second * 1000L
            if (duration != 0L) {
                timeCountingTextView.startCount(duration, isCountUp)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            DurationPickerDialog(this, callback).showWithCenteredButtons()
        } else {
            TimePickerDialog(this, { _, hour, minute -> callback(hour, minute, 0, false) },
                0, 0, true).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create root view
        rootView = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            gravity = Gravity.CENTER
        }
        setContentView(rootView)

        // Hide navigation bar
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB
            && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE
            window.decorView.systemUiVisibility = uiOptions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.setDecorFitsSystemWindows(false)
            }
        }

        // Apply background effects
        // API 31 and above has native support for window blur
        // See `android:windowBlurBehindEnabled` in themes.xml
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            // Apply blur to window using Android flags
            // This is used for API < 14, as it was deprecated for API 14-31.
            window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
        }

        // Dim window using Android flags
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window.attributes.dimAmount = 0.48f

        // Set-up animator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            backgroundValueAnimator = BackgroundValueAnimator(rootView)
        }

        // Create countdown clock
        timeCountingTextView = TimeCountingTextView(this).apply {
            onFinishListener = {
                completionFlashing = true
            }
            keepScreenOn = true
        }
        rootView.addView(timeCountingTextView)

        // Set-up double click mode switching
        detector = GestureDetector(this, this)
        detector.setOnDoubleTapListener(this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (detector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        if (completionFlashing) completionFlashing = false
        return true
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        if (completionFlashing) completionFlashing = false
        val mode = timeCountingTextView.displayMode + 1
        timeCountingTextView.displayMode =
            if (mode <= TextArtGenerator.maxTextArtFontsIndex) mode else 0
        return true
    }

    override fun onLongPress(event: MotionEvent) {
        if (completionFlashing) completionFlashing = false
        createDurationPickerDialog() // Set-up duration picker dialog
    }

    /* Unused implementations */
    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        return true
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent) { /* default */ }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return true
    }
}