/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.location.live.map

import com.fvslnlqb.chat.core.platform.VectorViewEvents
import com.fvslnlqb.chat.features.location.LocationData

sealed interface LiveLocationMapViewEvents : VectorViewEvents {
    data class LiveLocationError(val error: Throwable) : LiveLocationMapViewEvents
    data class ZoomToUserLocation(val userLocation: LocationData) : LiveLocationMapViewEvents
    object UserLocationNotAvailableError : LiveLocationMapViewEvents
}
