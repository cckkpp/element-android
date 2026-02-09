/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.settings.devices.v2.rename

import com.fvslnlqb.chat.core.platform.VectorViewModelAction

sealed class RenameSessionAction : VectorViewModelAction {
    object InitWithLastEditedName : RenameSessionAction()
    object SaveModifications : RenameSessionAction()
    data class EditLocally(val editedName: String) : RenameSessionAction()
}
