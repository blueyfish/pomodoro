// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro.ui

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.text.style.ReplacementSpan

// Based on https://gist.github.com/ok3141/3a3d142adc2e30a203a8211adfc65bd8
class MonospaceSpan : ReplacementSpan() {
    private var ignoreFullText = false

    fun setIgnoreFullText(ignoreFullText: Boolean) {
        this.ignoreFullText = ignoreFullText
    }

    private fun getMaxCharWidth(
        paint: Paint, text: CharSequence,
        start: Int, end: Int,
        widths: FloatArray?
    ): Int {
        val floatArray = widths.takeUnless { it == null } ?: FloatArray(end - start)
        paint.getTextWidths(text, start, end, floatArray)

        var max = 0f
        for (w in floatArray) {
            if (max < w) {
                max = w
            }
        }

        return Math.round(max)
    }

    override fun getSize(
        paint: Paint, text: CharSequence,
        start: Int, end: Int,
        fm: FontMetricsInt?
    ): Int {
        if (fm != null) paint.getFontMetricsInt(fm)

        var count = end - start
        if (text[start] == '\n') count -= 1
        if (text[end - 1] == '\n') count -= 1
        if (count < 0) count = 0

        return if (ignoreFullText) {
            getMaxCharWidth(paint, text, start, end, null) * count
        } else {
            getMaxCharWidth(paint, text, 0, text.length, null) * count
        }
    }

    override fun draw(
        canvas: Canvas, text: CharSequence,
        start: Int, end: Int,
        x: Float, top: Int, y: Int, bottom: Int,
        paint: Paint
    ) {
        val widths = FloatArray(end - start)
        val max = if (ignoreFullText) {
            getMaxCharWidth(paint, text, start, end, widths)
        } else {
            getMaxCharWidth(paint, text, 0, text.length, null)
        }

        var i = 0
        val n = end - start
        while (i < n) {
            val p = (max - widths[i]) / 2
            canvas.drawText(text, start + i, start + i + 1,
                x + max * i + p, y.toFloat(), paint)
            ++i
        }
    }
}
