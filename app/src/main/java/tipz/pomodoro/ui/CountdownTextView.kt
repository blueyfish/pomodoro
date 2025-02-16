// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.os.CountDownTimer
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import tipz.pomodoro.R
import tipz.pomodoro.textart.TextArtGenerator
import tipz.pomodoro.textart.c1Font


class CountdownTextView(context: Context) : TextView(context) {
    private var countDownTimer: CountDownTimer? = null
    var timerCounting = false
    var onFinishListener = {}
    var actualText = ""
    var displayMode = -1
        set(value) {
            field = value
            when (value) {
                0 -> {
                    maxLines = 1
                    setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(R.dimen.timer_mode_textview_size))
                }
                1 -> {
                    maxLines = 7
                    setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(R.dimen.timer_mode_art_size))
                }
                else -> { }
            }
            setText(actualText)
        }
    var maxHour = 96

    init {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        gravity = Gravity.CENTER

        // Set default styles
        typeface = Typeface.MONOSPACE
        setTextColor(Color.WHITE)
        setShadowLayer(1.5f, -2f, 2f, Color.GRAY)
        displayMode = 0

        // Ensure default text is shown
        resetCountdown()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        // Set the display mode in order to redraw
        displayMode = displayMode
    }

    fun setText(s: String) {
        actualText = s
        text = when (displayMode) {
            0 -> s
            1 -> TextArtGenerator.fromString(c1Font(), s)
            else -> ""
        }
    }

    fun startCountdown(duration: Long) {
        timerCounting = true
        countDownTimer = object : CountDownTimer(duration, 1000) {

            @SuppressLint("DefaultLocale")
            override fun onTick(millisUntilFinished: Long) {
                val hour = (millisUntilFinished / 3600000) % maxHour
                val min = (millisUntilFinished / 60000) % 60
                val sec = (millisUntilFinished / 1000) % 60

                // Used for formatting digits to be in 2 digits only
                setText(String.format("%02d:%02d:%02d", hour, min, sec))
            }

            override fun onFinish() {
                countDownTimer = null
                resetCountdown()
                onFinishListener()
            }
        }.start()
    }

    @SuppressLint("SetTextI18n")
    fun resetCountdown() {
        timerCounting = false
        if (countDownTimer != null) countDownTimer?.cancel()
        setText("00:00:00")
    }
}