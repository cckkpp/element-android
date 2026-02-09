/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.media

import com.fvslnlqb.chat.core.platform.VectorViewModelAction
import java.io.File

sealed class VectorAttachmentViewerAction : VectorViewModelAction {
    data class DownloadMedia(val file: File) : VectorAttachmentViewerAction()
}
