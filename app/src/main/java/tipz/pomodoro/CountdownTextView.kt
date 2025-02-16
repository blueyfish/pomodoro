// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro

import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView

class CountdownTextView(context: Context) : TextView(context) {
    private var countDownTimer: CountDownTimer? = null
    var timerCounting = false
    var onFinishListener = {}

    init {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        maxLines = 1
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 72f)
        resetCountdown()
    }

    fun startCountdown(duration: Long) {
        timerCounting = true
        countDownTimer = object : CountDownTimer(duration, 1000) {

            @SuppressLint("DefaultLocale")
            override fun onTick(millisUntilFinished: Long) {
                val hour = (millisUntilFinished / 3600000) % 24
                val min = (millisUntilFinished / 60000) % 60
                val sec = (millisUntilFinished / 1000) % 60

                // Used for formatting digits to be in 2 digits only
                text = String.format("%02d:%02d:%02d", hour, min, sec)
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
        text = "00:00:00"
    }
}