/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home.room.list.home.release

import com.fvslnlqb.chat.core.platform.VectorViewModelAction

sealed class ReleaseNotesAction : VectorViewModelAction {
    data class NextPressed(
            val isLastItemSelected: Boolean = false
    ) : ReleaseNotesAction()
    data class PageSelected(
            val selectedPageIndex: Int  = 0
    ) : ReleaseNotesAction()
}
