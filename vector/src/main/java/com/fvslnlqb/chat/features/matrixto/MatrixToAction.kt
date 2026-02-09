/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.matrixto

import com.fvslnlqb.chat.core.platform.VectorViewModelAction
import org.matrix.android.sdk.api.util.MatrixItem

sealed class MatrixToAction : VectorViewModelAction {
    data class StartChattingWithUser(val matrixItem: MatrixItem) : MatrixToAction()
    object FailedToResolveUser : MatrixToAction()
    object FailedToStartChatting : MatrixToAction()
    data class JoinSpace(val spaceID: String, val viaServers: List<String>?) : MatrixToAction()
    data class JoinRoom(val roomIdOrAlias: String, val viaServers: List<String>?) : MatrixToAction()
    data class OpenSpace(val spaceID: String) : MatrixToAction()
    data class OpenRoom(val roomId: String) : MatrixToAction()
}
