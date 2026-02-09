/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.analytics

import com.fvslnlqb.chat.features.analytics.itf.VectorAnalyticsEvent
import com.fvslnlqb.chat.features.analytics.itf.VectorAnalyticsScreen
import com.fvslnlqb.chat.features.analytics.plan.SuperProperties
import com.fvslnlqb.chat.features.analytics.plan.UserProperties

interface AnalyticsTracker {
    /**
     * Capture an Event.
     */
    fun capture(event: VectorAnalyticsEvent)

    /**
     * Track a displayed screen.
     */
    fun screen(screen: VectorAnalyticsScreen)

    /**
     * Update user specific properties.
     */
    fun updateUserProperties(userProperties: UserProperties)

    /**
     * Update the super properties.
     * Super properties are added to any tracked event automatically.
     */
    fun updateSuperProperties(updatedProperties: SuperProperties)
}
