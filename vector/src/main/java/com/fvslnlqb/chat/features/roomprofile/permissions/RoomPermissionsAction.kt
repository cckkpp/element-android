/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.roomprofile.permissions

import com.fvslnlqb.chat.core.platform.VectorViewModelAction
import org.matrix.android.sdk.api.session.room.powerlevels.UserPowerLevel

sealed class RoomPermissionsAction : VectorViewModelAction {
    object ToggleShowAllPermissions : RoomPermissionsAction()

    data class UpdatePermission(val editablePermission: EditablePermission, val powerLevel: UserPowerLevel.Value) : RoomPermissionsAction()
}
