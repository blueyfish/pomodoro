// Copyright (c) 2020 Danilo Arcadipane
// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro

import android.os.Handler
import android.view.View

@Suppress("DEPRECATION")
open class DoubleClick(
    private val doubleClickListener: DoubleClickListener,
    private var interval: Long = 200L
) : View.OnClickListener {

    private val handler = Handler()
    private var counterClicks = 0
    private var isBusy = false

    override fun onClick(view: View) {
        if (!isBusy) {
            isBusy = true

            counterClicks++
            handler.postDelayed({
                if (counterClicks >= 2) {
                    doubleClickListener.onDoubleClickEvent(view)
                }
                if (counterClicks == 1) {
                    doubleClickListener.onSingleClickEvent(view)
                }

                counterClicks = 0
            }, interval)
            isBusy = false
        }
    }
}