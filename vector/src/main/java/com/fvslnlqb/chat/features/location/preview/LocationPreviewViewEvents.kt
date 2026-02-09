/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.location.preview

import com.fvslnlqb.chat.core.platform.VectorViewEvents
import com.fvslnlqb.chat.features.location.LocationData

sealed class LocationPreviewViewEvents : VectorViewEvents {
    data class ZoomToUserLocation(val userLocation: LocationData) : LocationPreviewViewEvents()
    object UserLocationNotAvailableError : LocationPreviewViewEvents()
}
