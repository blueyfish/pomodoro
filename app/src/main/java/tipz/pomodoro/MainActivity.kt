// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro

import android.app.Activity
import android.app.TimePickerDialog
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import tipz.pomodoro.ui.doubleclick.DoubleClick
import tipz.pomodoro.ui.doubleclick.DoubleClickListener
import tipz.pomodoro.ui.BackgroundValueAnimator
import tipz.pomodoro.ui.CountdownTextView
import tipz.pomodoro.ui.DurationPickerDialog


@Suppress("DEPRECATION")
class MainActivity : Activity() {
    private lateinit var rootView: LinearLayout
    private lateinit var countDownTextView: CountdownTextView

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

    private fun createDurationPickerDialog() {
        val callback = { hour: Int, minute: Int, second: Int ->
            if (countDownTextView.timerCounting) countDownTextView.resetCountdown()

            val duration = hour * 3600000L + minute * 60000L + second * 1000L
            if (duration != 0L) {
                countDownTextView.startCountdown(duration)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            DurationPickerDialog(this, callback).show()
        } else {
            TimePickerDialog(this, { _, hour, minute -> callback(hour, minute, 0) },
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
        window.attributes.dimAmount = 0.32f

        // Set-up animator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            backgroundValueAnimator = BackgroundValueAnimator(rootView)
        }

        // Create countdown clock
        countDownTextView = CountdownTextView(this).apply {
            onFinishListener = {
                completionFlashing = true
            }
        }
        rootView.addView(countDownTextView)

        // Set-up double click mode switching
        rootView.setOnClickListener(
            DoubleClick(object : DoubleClickListener {
                override fun onSingleClickEvent(view: View?) {
                    if (completionFlashing) completionFlashing = false
                }

                override fun onDoubleClickEvent(view: View?) {
                    if (completionFlashing) completionFlashing = false
                    val mode = countDownTextView.displayMode + 1
                    countDownTextView.displayMode = if (mode <= 1) mode else 0
                }
            })
        )

        // Set-up duration picker dialog
        rootView.setOnLongClickListener {
            if (completionFlashing) completionFlashing = false
            createDurationPickerDialog()
            true
        }
    }
}