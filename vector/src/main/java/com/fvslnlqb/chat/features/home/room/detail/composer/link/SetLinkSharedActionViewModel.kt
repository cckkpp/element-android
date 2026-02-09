/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */
package com.fvslnlqb.chat.features.home.room.detail.composer.link

import com.fvslnlqb.chat.core.platform.VectorSharedAction
import com.fvslnlqb.chat.core.platform.VectorSharedActionViewModel
import javax.inject.Inject

class SetLinkSharedActionViewModel @Inject constructor() :
        VectorSharedActionViewModel<SetLinkSharedAction>()

sealed interface SetLinkSharedAction : VectorSharedAction {
    data class Set(
            val link: String,
    ) : SetLinkSharedAction

    data class Insert(
            val text: String,
            val link: String,
    ) : SetLinkSharedAction

    object Remove : SetLinkSharedAction
}
