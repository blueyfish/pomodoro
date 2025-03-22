// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro.ui

import android.app.AlertDialog
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout

// Based on https://stackoverflow.com/a/58874572/10866268
fun AlertDialog.showWithCenteredButtons() {
    // Fire away with the dialog before centering
    show()

    // Get dialog buttons
    val positive = getButton(AlertDialog.BUTTON_POSITIVE)
    val negative = getButton(AlertDialog.BUTTON_NEGATIVE)

    // Disable the material spacer view in case there is one
    val parent = positive.parent as? LinearLayout
    parent?.gravity = Gravity.CENTER_HORIZONTAL
    parent?.weightSum = 2f
    val leftSpacer = parent?.getChildAt(1)
    if (leftSpacer !is Button) leftSpacer?.visibility = View.GONE

    // Force the default buttons to center
    val layoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )

    layoutParams.weight = 1f
    layoutParams.gravity = Gravity.CENTER

    positive.layoutParams = layoutParams
    negative.layoutParams = layoutParams
}