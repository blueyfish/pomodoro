package tipz.pomodoro

import android.view.View

interface DoubleClickListener {
    fun onSingleClickEvent(view: View?)
    fun onDoubleClickEvent(view: View?)
}