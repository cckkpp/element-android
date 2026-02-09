/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.core.platform

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.fvslnlqb.chat.core.utils.EventQueue
import com.fvslnlqb.chat.core.utils.SharedEvents

abstract class VectorViewModel<S : MavericksState, VA : VectorViewModelAction, VE : VectorViewEvents>(initialState: S) :
        MavericksViewModel<S>(initialState) {

    // Used to post transient events to the View
    protected val _viewEvents = EventQueue<VE>(capacity = 64)
    val viewEvents: SharedEvents<VE>
        get() = _viewEvents

    abstract fun handle(action: VA)
}
