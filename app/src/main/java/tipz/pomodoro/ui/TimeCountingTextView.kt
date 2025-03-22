// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.CountDownTimer
import android.text.SpannableString
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import tipz.pomodoro.R
import tipz.pomodoro.textart.TextArtGenerator

class TimeCountingTextView(context: Context) : TextView(context) {
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
                        resources.getDimension(R.dimen.timer_mode_default_size))
                }
                else -> {
                    if (value > TextArtGenerator.maxTextArtFontsIndex) return
                    TextArtGenerator.textArtFonts[value]?.let {
                        maxLines = it.fontHeight
                        setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            resources.getDimension(it.fontTextSize))
                    }
                }
            }
            setText(actualText)
        }

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
        resetCount()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        // Set the display mode in order to redraw
        displayMode = displayMode
    }

    fun setText(s: String) {
        actualText = s
        val text = when (displayMode) {
            0 -> s
            else -> TextArtGenerator.fromString(displayMode, s).let {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) it
                else SpannableString(it).apply {
                    setSpan(MonospaceSpan(), 0, length, 0)
                }
            }
        }
        super.setText(text)
    }

    fun startCount(duration: Long, isCountUp: Boolean) {
        timerCounting = true
        countDownTimer = object : TimeCounter(duration, isCountUp) {
            override fun onTick(formatedString: String) {
                setText(formatedString)
            }

            override fun onFinish() {
                countDownTimer = null
                resetCount()
                onFinishListener()
            }
        }.start()
    }

    @SuppressLint("SetTextI18n")
    fun resetCount() {
        timerCounting = false
        if (countDownTimer != null) countDownTimer?.cancel()
        setText("00:00:00")
    }
}