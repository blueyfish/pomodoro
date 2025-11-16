// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro.ui

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.ToggleButton
import tipz.pomodoro.getFrameworkIdentifier

@SuppressLint("SetTextI18n")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class DurationPickerDialog(
    context: Context,
    callbackCompletion: (hour: Int, minute: Int, second: Int, isCountUp: Boolean) -> Unit
) : AlertDialog(context) {
    private val maxHour = 96

    private val hour = NumberPicker(context)
    private val minute = NumberPicker(context)
    private val second = NumberPicker(context)
    private val countDirection = ToggleButton(context)

    init {
        // Set-up root view
        val rootView = LinearLayout(context).apply {
            gravity = Gravity.CENTER
            orientation = LinearLayout.VERTICAL
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
        countDirection.apply {
            // These strings are set intentionally and require no translation
            textOff = "DOWN"
            textOn = "UP"
            text = "DOWN" // Default as countdown
            setOnCheckedChangeListener { _, b ->
                if (b) {
                    hour.value = hour.maxValue
                    minute.value = minute.maxValue
                    second.value = second.maxValue
                    hour.isEnabled = false
                    minute.isEnabled = false
                    second.isEnabled = false
                } else {
                    hour.value = hour.minValue
                    minute.value = minute.minValue
                    second.value = second.minValue
                    hour.isEnabled = true
                    minute.isEnabled = true
                    second.isEnabled = true
                }
            }
        }
        rootView.addView(LinearLayout(context).apply {
            gravity = Gravity.CENTER
            addView(hour)
            addView(minute)
            addView(second)
        })
        rootView.addView(countDirection)

        setButton(BUTTON_POSITIVE, context.resources.getString(
            context.getFrameworkIdentifier("date_time_set"))
        ) { _: DialogInterface, _: Int ->
            callbackCompletion(hour.value, minute.value, second.value, countDirection.isChecked)
        }

        setButton(BUTTON_NEGATIVE, context.resources.getString(
            android.R.string.cancel)) { _: DialogInterface, _: Int -> /* default */ }
    }
}