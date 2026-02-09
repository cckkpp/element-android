/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.location.live.map

import com.fvslnlqb.chat.core.platform.VectorViewModelAction

sealed class LiveLocationMapAction : VectorViewModelAction {
    data class AddMapSymbol(val key: String, val value: Long) : LiveLocationMapAction()
    data class RemoveMapSymbol(val key: String) : LiveLocationMapAction()
    object StopSharing : LiveLocationMapAction()
    object ShowMapLoadingError : LiveLocationMapAction()
    object ZoomToUserLocation : LiveLocationMapAction()
}
