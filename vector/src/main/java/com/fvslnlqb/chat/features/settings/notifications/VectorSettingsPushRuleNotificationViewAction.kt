/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.settings.notifications

import com.fvslnlqb.chat.core.platform.VectorViewModelAction

sealed interface VectorSettingsPushRuleNotificationViewAction : VectorViewModelAction {
    data class UpdatePushRule(val ruleId: String, val checked: Boolean) : VectorSettingsPushRuleNotificationViewAction
}
