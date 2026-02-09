/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.attachments.preview

import android.net.Uri
import com.fvslnlqb.chat.core.platform.VectorViewModelAction

sealed class AttachmentsPreviewAction : VectorViewModelAction {
    object RemoveCurrentAttachment : AttachmentsPreviewAction()
    data class SetCurrentAttachment(val index: Int) : AttachmentsPreviewAction()
    data class UpdatePathOfCurrentAttachment(val newUri: Uri) : AttachmentsPreviewAction()
}
