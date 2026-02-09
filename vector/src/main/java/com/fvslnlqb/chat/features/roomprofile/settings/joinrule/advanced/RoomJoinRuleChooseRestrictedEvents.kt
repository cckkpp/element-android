/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.roomprofile.settings.joinrule.advanced

import com.fvslnlqb.chat.core.platform.VectorViewEvents

sealed class RoomJoinRuleChooseRestrictedEvents : VectorViewEvents {
    object NavigateToChooseRestricted : RoomJoinRuleChooseRestrictedEvents()
    data class NavigateToUpgradeRoom(val roomId: String, val toVersion: String, val description: CharSequence) : RoomJoinRuleChooseRestrictedEvents()
}
