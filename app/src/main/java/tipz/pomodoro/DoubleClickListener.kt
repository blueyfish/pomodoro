// Copyright (c) 2020 Danilo Arcadipane
// Copyright (c) 2025 Tipz Team
// SPDX-License-Identifier: Apache-2.0

package tipz.pomodoro

import android.view.View

interface DoubleClickListener {
    fun onSingleClickEvent(view: View?)
    fun onDoubleClickEvent(view: View?)
}