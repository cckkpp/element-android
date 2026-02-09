/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.settings.font

import com.fvslnlqb.chat.core.platform.VectorViewModelAction
import com.fvslnlqb.chat.features.settings.FontScaleValue

sealed class FontScaleSettingAction : VectorViewModelAction {
    data class UseSystemSettingChangedAction(val useSystemSettings: Boolean) : FontScaleSettingAction()
    data class FontScaleChangedAction(val fontScale: FontScaleValue) : FontScaleSettingAction()
}
