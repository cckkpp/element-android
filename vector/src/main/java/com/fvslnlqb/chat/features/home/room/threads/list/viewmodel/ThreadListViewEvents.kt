/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home.room.threads.list.viewmodel

import com.fvslnlqb.chat.core.platform.VectorViewEvents

sealed interface ThreadListViewEvents : VectorViewEvents {
    data class ShowError(val throwable: Throwable) : ThreadListViewEvents
}
