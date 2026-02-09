/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.location.preview

import com.fvslnlqb.chat.core.platform.VectorViewModelAction

sealed class LocationPreviewAction : VectorViewModelAction {
    object ShowMapLoadingError : LocationPreviewAction()
    object ZoomToUserLocation : LocationPreviewAction()
}
