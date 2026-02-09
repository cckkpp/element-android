/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */
package com.fvslnlqb.chat.features.attachments

import com.fvslnlqb.chat.core.platform.VectorSharedAction
import com.fvslnlqb.chat.core.platform.VectorSharedActionViewModel
import javax.inject.Inject

class AttachmentTypeSelectorSharedActionViewModel @Inject constructor() :
        VectorSharedActionViewModel<AttachmentTypeSelectorSharedAction>()

sealed interface AttachmentTypeSelectorSharedAction : VectorSharedAction {
    data class SelectAttachmentTypeAction(
            val attachmentType: AttachmentType
    ) : AttachmentTypeSelectorSharedAction
}
