package tipz.pomodoro

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.View

@SuppressLint("NewApi")
class BackgroundValueAnimator(v: View) : ValueAnimator() {
    init {
        setIntValues(flashClearColor, flashCyanColor)
        setEvaluator(ArgbEvaluator())
        addUpdateListener {
            v.setBackgroundColor(animatedValue as Int)
        }
        duration = 800
        repeatCount = INFINITE
    }

    companion object {
        const val flashClearColor = 0
        const val flashCyanColor = 0x7F00FFFF
    }
}