/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.analytics.extensions

import com.fvslnlqb.chat.features.analytics.plan.Composer
import com.fvslnlqb.chat.features.home.room.detail.composer.MessageComposerViewState
import com.fvslnlqb.chat.features.home.room.detail.composer.SendMode

fun MessageComposerViewState.toAnalyticsComposer(): Composer =
        Composer(
                inThread = isInThreadTimeline(),
                isEditing = sendMode is SendMode.Edit,
                isReply = sendMode is SendMode.Reply,
                messageType = Composer.MessageType.Text,
                startsThread = startsThread,
        )
