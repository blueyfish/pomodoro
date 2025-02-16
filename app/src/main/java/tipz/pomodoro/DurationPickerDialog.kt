// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.NumberPicker

@Suppress("DEPRECATION")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class DurationPickerDialog(
    context: Context,
    callbackCompletion: (hour: Int, minute: Int, second: Int) -> Unit
) : AlertDialog(context) {
    private val maxHour = 96

    private val hour = NumberPicker(context)
    private val minute = NumberPicker(context)
    private val second = NumberPicker(context)

    init {
        // Set-up root view
        val rootView = LinearLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            gravity = Gravity.CENTER
        }
        setView(rootView)

        // Set-up hour, minute and second digit
        hour.apply {
            maxValue = maxHour - 1
            minValue = 0
            wrapSelectorWheel = true
        }
        minute.apply {
            maxValue = 59
            minValue = 0
            wrapSelectorWheel = true
            setOnValueChangedListener { _: NumberPicker, i: Int, i2: Int ->
                if (i == maxValue && i2 == minValue) hour.value++
                if (i == minValue && i2 == maxValue) hour.value--
            }
        }
        second.apply {
            maxValue = 59
            minValue = 0
            wrapSelectorWheel = true
            setOnValueChangedListener { _: NumberPicker, i: Int, i2: Int ->
                if (i == maxValue && i2 == minValue) {
                    if (minute.value == minute.maxValue) hour.value++
                    minute.value++
                }
                if (i == minValue && i2 == maxValue) {
                    if (minute.value == minute.minValue) hour.value--
                    minute.value--
                }
            }
        }
        rootView.addView(hour)
        rootView.addView(minute)
        rootView.addView(second)

        setButton(context.resources.getString(android.R.string.ok)) { _: DialogInterface, _: Int ->
            callbackCompletion(hour.value, minute.value, second.value)
        }

        setButton2(context.resources.getString(android.R.string.cancel)) { _: DialogInterface, _: Int ->
        }
    }
}