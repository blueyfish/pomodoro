// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.View

@SuppressLint("NewApi")
class BackgroundValueAnimator(private val v: View) : ValueAnimator() {
    init {
        setIntValues(flashClearColor, flashCyanColor)
        setEvaluator(ArgbEvaluator())
        addUpdateListener {
            v.setBackgroundColor(animatedValue as Int)
        }
        duration = 1000
        repeatCount = INFINITE
    }

    fun stop() {
        cancel()
        v.setBackgroundColor(flashClearColor)
    }

    companion object {
        const val flashClearColor = 0
        const val flashCyanColor = 0x7F00FFFF
    }
}