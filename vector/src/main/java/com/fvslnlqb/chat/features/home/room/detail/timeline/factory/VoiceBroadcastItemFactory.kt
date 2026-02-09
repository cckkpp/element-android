/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */
package com.fvslnlqb.chat.features.home.room.detail.timeline.factory

import com.fvslnlqb.chat.core.error.ErrorFormatter
import com.fvslnlqb.chat.core.resources.ColorProvider
import com.fvslnlqb.chat.core.resources.DrawableProvider
import com.fvslnlqb.chat.features.home.room.detail.timeline.helper.AudioMessagePlaybackTracker
import com.fvslnlqb.chat.features.home.room.detail.timeline.helper.AvatarSizeProvider
import com.fvslnlqb.chat.features.home.room.detail.timeline.helper.VoiceBroadcastEventsGroup
import com.fvslnlqb.chat.features.home.room.detail.timeline.item.AbsMessageItem
import com.fvslnlqb.chat.features.home.room.detail.timeline.item.AbsMessageVoiceBroadcastItem
import com.fvslnlqb.chat.features.home.room.detail.timeline.item.BaseEventItem
import com.fvslnlqb.chat.features.home.room.detail.timeline.item.MessageVoiceBroadcastListeningItem
import com.fvslnlqb.chat.features.home.room.detail.timeline.item.MessageVoiceBroadcastListeningItem_
import com.fvslnlqb.chat.features.home.room.detail.timeline.item.MessageVoiceBroadcastRecordingItem
import com.fvslnlqb.chat.features.home.room.detail.timeline.item.MessageVoiceBroadcastRecordingItem_
import com.fvslnlqb.chat.features.voicebroadcast.listening.VoiceBroadcastPlayer
import com.fvslnlqb.chat.features.voicebroadcast.model.MessageVoiceBroadcastInfoContent
import com.fvslnlqb.chat.features.voicebroadcast.model.VoiceBroadcast
import com.fvslnlqb.chat.features.voicebroadcast.model.VoiceBroadcastState
import com.fvslnlqb.chat.features.voicebroadcast.model.asVoiceBroadcastEvent
import com.fvslnlqb.chat.features.voicebroadcast.recording.VoiceBroadcastRecorder
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.getRoom
import org.matrix.android.sdk.api.util.toMatrixItem
import javax.inject.Inject

class VoiceBroadcastItemFactory @Inject constructor(
        private val session: Session,
        private val avatarSizeProvider: AvatarSizeProvider,
        private val colorProvider: ColorProvider,
        private val drawableProvider: DrawableProvider,
        private val errorFormatter: ErrorFormatter,
        private val voiceBroadcastRecorder: VoiceBroadcastRecorder?,
        private val voiceBroadcastPlayer: VoiceBroadcastPlayer,
        private val playbackTracker: AudioMessagePlaybackTracker,
        private val noticeItemFactory: NoticeItemFactory,
) {

    fun create(
            params: TimelineItemFactoryParams,
            messageContent: MessageVoiceBroadcastInfoContent,
            highlight: Boolean,
            attributes: AbsMessageItem.Attributes,
    ): BaseEventItem<*>? {
        // Only display item of the initial event with updated data
        if (messageContent.voiceBroadcastState != VoiceBroadcastState.STARTED) {
            return noticeItemFactory.create(params)
        }

        val voiceBroadcastEventsGroup = params.eventsGroup?.let { VoiceBroadcastEventsGroup(it) } ?: return null
        val voiceBroadcastEvent = voiceBroadcastEventsGroup.getLastDisplayableEvent().root.asVoiceBroadcastEvent() ?: return null
        val voiceBroadcastContent = voiceBroadcastEvent.content ?: return null
        val voiceBroadcast = VoiceBroadcast(voiceBroadcastId = voiceBroadcastEventsGroup.voiceBroadcastId, roomId = params.event.roomId)

        val isRecording = voiceBroadcastContent.voiceBroadcastState != VoiceBroadcastState.STOPPED &&
                voiceBroadcastEvent.root.stateKey == session.myUserId &&
                messageContent.deviceId == session.sessionParams.deviceId

        val voiceBroadcastAttributes = AbsMessageVoiceBroadcastItem.Attributes(
                voiceBroadcast = voiceBroadcast,
                voiceBroadcastState = voiceBroadcastContent.voiceBroadcastState,
                duration = voiceBroadcastEventsGroup.getDuration(),
                hasUnableToDecryptEvent = voiceBroadcastEventsGroup.hasUnableToDecryptEvent(),
                recorderName = params.event.senderInfo.disambiguatedDisplayName,
                recorder = voiceBroadcastRecorder,
                player = voiceBroadcastPlayer,
                playbackTracker = playbackTracker,
                roomItem = session.getRoom(params.event.roomId)?.roomSummary()?.toMatrixItem(),
                colorProvider = colorProvider,
                drawableProvider = drawableProvider,
                errorFormatter = errorFormatter,
        )

        return if (isRecording) {
            createRecordingItem(highlight, attributes, voiceBroadcastAttributes)
        } else {
            createListeningItem(highlight, attributes, voiceBroadcastAttributes)
        }
    }

    private fun createRecordingItem(
            highlight: Boolean,
            attributes: AbsMessageItem.Attributes,
            voiceBroadcastAttributes: AbsMessageVoiceBroadcastItem.Attributes,
    ): MessageVoiceBroadcastRecordingItem {
        return MessageVoiceBroadcastRecordingItem_()
                .id("voice_broadcast_${voiceBroadcastAttributes.voiceBroadcast.voiceBroadcastId}")
                .attributes(attributes)
                .voiceBroadcastAttributes(voiceBroadcastAttributes)
                .highlighted(highlight)
                .leftGuideline(avatarSizeProvider.leftGuideline)
    }

    private fun createListeningItem(
            highlight: Boolean,
            attributes: AbsMessageItem.Attributes,
            voiceBroadcastAttributes: AbsMessageVoiceBroadcastItem.Attributes,
    ): MessageVoiceBroadcastListeningItem {
        return MessageVoiceBroadcastListeningItem_()
                .id("voice_broadcast_${voiceBroadcastAttributes.voiceBroadcast.voiceBroadcastId}")
                .attributes(attributes)
                .voiceBroadcastAttributes(voiceBroadcastAttributes)
                .highlighted(highlight)
                .leftGuideline(avatarSizeProvider.leftGuideline)
    }
}
