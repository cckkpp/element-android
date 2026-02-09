/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.matrixto

import com.fvslnlqb.chat.core.platform.VectorViewEvents

sealed class MatrixToViewEvents : VectorViewEvents {
    data class NavigateToRoom(val roomId: String) : MatrixToViewEvents()
    data class NavigateToSpace(val spaceId: String) : MatrixToViewEvents()
    data class ShowModalError(val error: String) : MatrixToViewEvents()
    object Dismiss : MatrixToViewEvents()
}
