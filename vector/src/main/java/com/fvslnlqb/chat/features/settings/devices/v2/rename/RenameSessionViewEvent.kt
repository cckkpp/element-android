/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.settings.devices.v2.rename

import com.fvslnlqb.chat.core.platform.VectorViewEvents

sealed class RenameSessionViewEvent : VectorViewEvents {
    data class Initialized(val deviceName: String) : RenameSessionViewEvent()
    object SessionRenamed : RenameSessionViewEvent()
    data class Failure(val throwable: Throwable) : RenameSessionViewEvent()
}
