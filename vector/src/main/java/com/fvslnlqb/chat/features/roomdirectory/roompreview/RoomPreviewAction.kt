/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.roomdirectory.roompreview

import com.fvslnlqb.chat.core.platform.VectorViewModelAction

sealed class RoomPreviewAction : VectorViewModelAction {
    object Join : RoomPreviewAction()
    object JoinThirdParty : RoomPreviewAction()
}
