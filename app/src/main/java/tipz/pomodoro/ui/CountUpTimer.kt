// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro.ui

import android.annotation.SuppressLint
import android.os.CountDownTimer

// Based on gist from Alco (GitHub @ricardoalcocer)
abstract class TimeCounter protected constructor(
    private val duration: Long, private val isCountUp: Boolean
) : CountDownTimer(duration, 1000) {
    var maxHour = 96

    @SuppressLint("DefaultLocale")
    override fun onTick(millisUntilFinished: Long) {
        val millis = if (isCountUp) duration - millisUntilFinished else millisUntilFinished

        val hour = (millis / 3600000) % maxHour
        val min = (millis / 60000) % 60
        val sec = (millis / 1000) % 60

        // Used for formatting digits to be in 2 digits only
        onTick(String.format("%02d:%02d:%02d", hour, min, sec))
    }
    abstract fun onTick(formatedString: String)
}