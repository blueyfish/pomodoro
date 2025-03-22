// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro.textart

object TextArtGenerator {
    val textArtFonts = hashMapOf(
        1 to c1Font(),
        2 to AnsiShawowFont()
    )
    val maxTextArtFontsIndex = textArtFonts.size

    fun fromString(i: Int, s: String) =
        textArtFonts[i].takeUnless { it == null }?.let {
            fromString(it, s)
        } ?: ""

    fun fromString(f: TextArtFont, s: String): String {
        val fontArray = arrayListOf<List<String>>()

        // Create an array of fonts from string
        for (i in s.indices) {
            val c: Char = s[i]
            val fc = when (c) {
                '0' -> f.zero
                '1' -> f.one
                '2' -> f.two
                '3' -> f.three
                '4' -> f.four
                '5' -> f.five
                '6' -> f.six
                '7' -> f.seven
                '8' -> f.eight
                '9' -> f.nine
                ':' -> f.colon
                else -> null
            }
            if (fc == null) continue
            fontArray.add(fc)
        }

        var result = ""
        for (i in 0..< f.fontHeight) {
            fontArray.forEach {
                result += it[i]
            }
            result += "\n"
        }
        return result
    }
}