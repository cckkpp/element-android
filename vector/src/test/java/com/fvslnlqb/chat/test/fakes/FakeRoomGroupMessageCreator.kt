/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.test.fakes

import com.fvslnlqb.chat.features.notifications.NotifiableMessageEvent
import com.fvslnlqb.chat.features.notifications.RoomGroupMessageCreator
import com.fvslnlqb.chat.features.notifications.RoomNotification
import io.mockk.every
import io.mockk.mockk

class FakeRoomGroupMessageCreator {

    val instance = mockk<RoomGroupMessageCreator>()

    fun givenCreatesRoomMessageFor(
            events: List<NotifiableMessageEvent>,
            roomId: String,
            userDisplayName: String,
            userAvatarUrl: String?
    ): RoomNotification.Message {
        val mockMessage = mockk<RoomNotification.Message>()
        every { instance.createRoomMessage(events, roomId, userDisplayName, userAvatarUrl) } returns mockMessage
        return mockMessage
    }
}
