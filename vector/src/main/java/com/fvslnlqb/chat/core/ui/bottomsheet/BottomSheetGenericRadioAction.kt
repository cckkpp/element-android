/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.core.ui.bottomsheet

import com.fvslnlqb.chat.core.epoxy.bottomsheet.BottomSheetRadioActionItem_
import com.fvslnlqb.chat.core.platform.VectorSharedAction

/**
 * Parent class for a bottom sheet action.
 */
open class BottomSheetGenericRadioAction(
        open val title: String?,
        open val description: String? = null,
        open val isSelected: Boolean
) : VectorSharedAction {

    fun toRadioBottomSheetItem(): BottomSheetRadioActionItem_ {
        return BottomSheetRadioActionItem_().also {
            it.id("action_$title")
            it.title(title)
            it.selected(isSelected)
            it.description(description)
        }
    }
}
