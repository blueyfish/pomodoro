// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("DiscouragedApi")
fun Context.getFrameworkIdentifier(name: String): Int =
    resources.getIdentifier(name, "string", "android")