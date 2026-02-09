/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.settings.devices.v2

import com.fvslnlqb.chat.core.session.clientinfo.MatrixClientInfoContent
import com.fvslnlqb.chat.features.settings.devices.v2.details.extended.DeviceExtendedInfo
import org.matrix.android.sdk.api.session.crypto.model.CryptoDeviceInfo
import org.matrix.android.sdk.api.session.crypto.model.DeviceInfo
import org.matrix.android.sdk.api.session.crypto.model.RoomEncryptionTrustLevel

data class DeviceFullInfo(
        val deviceInfo: DeviceInfo,
        val cryptoDeviceInfo: CryptoDeviceInfo?,
        val roomEncryptionTrustLevel: RoomEncryptionTrustLevel?,
        val isInactive: Boolean,
        val isCurrentDevice: Boolean,
        val deviceExtendedInfo: DeviceExtendedInfo,
        val matrixClientInfo: MatrixClientInfoContent?,
        val isSelected: Boolean = false,
)
