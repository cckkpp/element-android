/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.room

import com.fvslnlqb.chat.core.platform.VectorViewEvents

sealed class RequireActiveMembershipViewEvents : VectorViewEvents {
    data class RoomLeft(val leftMessage: String?) : RequireActiveMembershipViewEvents()
}
