// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro.ui

import android.annotation.SuppressLint
import android.os.CountDownTimer

// Based on gist from Alco (GitHub @ricardoalcocer)
abstract class TimeCounter protected constructor(
    duration: Long, private val isCountUp: Boolean,
    private val storedDuration: Long = 0L
) : CountDownTimer(duration, 1000) {

    @SuppressLint("DefaultLocale")
    override fun onTick(millisUntilFinished: Long) {
        val millis =
            if (isCountUp) maxMillis - millisUntilFinished + storedDuration
            else millisUntilFinished

        val hour = (millis / 3600000) % maxHour
        val min = (millis / 60000) % 60
        val sec = (millis / 1000) % 60

        // Used for formatting digits to be in 2 digits only
        onTick(String.format("%02d:%02d:%02d", hour, min, sec))
    }
    abstract fun onTick(formatedString: String)

    companion object {
        var maxHour = 96
        var maxMillis = 345599000L /* Hardcoded value for 95:59:59 */

        fun reverseMillisFormat(formatedString: String): Long {
            var millis = 0L
            formatedString.split(':').map { it.toInt() }.forEachIndexed { i, it ->
                when (i) {
                    0 -> millis += it * 3600000
                    1 -> millis += it * 60000
                    2 -> millis += it * 1000
                }
            }
            return millis
        }
    }
}