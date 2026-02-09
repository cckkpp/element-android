/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home.room.detail.timeline

import androidx.annotation.ColorInt
import com.fvslnlqb.chat.core.resources.ColorProvider
import com.fvslnlqb.chat.features.home.room.detail.timeline.helper.MatrixItemColorProvider
import com.fvslnlqb.chat.features.settings.VectorPreferences
import org.matrix.android.sdk.api.session.room.send.SendState
import org.matrix.android.sdk.api.util.MatrixItem
import javax.inject.Inject

class MessageColorProvider @Inject constructor(
        private val colorProvider: ColorProvider,
        private val matrixItemColorProvider: MatrixItemColorProvider,
        private val vectorPreferences: VectorPreferences
) {

    @ColorInt
    fun getMemberNameTextColor(matrixItem: MatrixItem): Int {
        return matrixItemColorProvider.getColor(matrixItem)
    }

    @ColorInt
    fun getMessageTextColor(sendState: SendState): Int {
        return if (vectorPreferences.developerMode()) {
            when (sendState) {
                // SendStates, in the classical order they will occur
                SendState.UNKNOWN,
                SendState.UNSENT -> colorProvider.getColorFromAttribute(im.vector.lib.ui.styles.R.attr.vctr_sending_message_text_color)
                SendState.ENCRYPTING -> colorProvider.getColorFromAttribute(im.vector.lib.ui.styles.R.attr.vctr_encrypting_message_text_color)
                SendState.SENDING -> colorProvider.getColorFromAttribute(im.vector.lib.ui.styles.R.attr.vctr_sending_message_text_color)
                SendState.SENT,
                SendState.SYNCED -> colorProvider.getColorFromAttribute(im.vector.lib.ui.styles.R.attr.vctr_message_text_color)
                SendState.UNDELIVERED,
                SendState.FAILED_UNKNOWN_DEVICES -> colorProvider.getColorFromAttribute(im.vector.lib.ui.styles.R.attr.vctr_unsent_message_text_color)
            }
        } else {
            // When not in developer mode, we use only one color
            colorProvider.getColorFromAttribute(im.vector.lib.ui.styles.R.attr.vctr_message_text_color)
        }
    }
}
