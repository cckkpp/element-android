/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.core.session

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import com.fvslnlqb.chat.core.extensions.startSyncing
import com.fvslnlqb.chat.core.notification.NotificationsSettingUpdater
import com.fvslnlqb.chat.core.notification.PushRulesUpdater
import com.fvslnlqb.chat.core.session.clientinfo.UpdateMatrixClientInfoUseCase
import com.fvslnlqb.chat.features.call.webrtc.WebRtcCallManager
import com.fvslnlqb.chat.features.session.coroutineScope
import com.fvslnlqb.chat.features.settings.VectorPreferences
import com.fvslnlqb.chat.features.settings.devices.v2.notification.UpdateNotificationSettingsAccountDataUseCase
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session
import timber.log.Timber
import javax.inject.Inject

class ConfigureAndStartSessionUseCase @Inject constructor(
        @ApplicationContext private val context: Context,
        private val webRtcCallManager: WebRtcCallManager,
        private val updateMatrixClientInfoUseCase: UpdateMatrixClientInfoUseCase,
        private val vectorPreferences: VectorPreferences,
        private val notificationsSettingUpdater: NotificationsSettingUpdater,
        private val updateNotificationSettingsAccountDataUseCase: UpdateNotificationSettingsAccountDataUseCase,
        private val pushRulesUpdater: PushRulesUpdater,
) {

    fun execute(session: Session, startSyncing: Boolean = true) {
        Timber.i("Configure and start session for ${session.myUserId}. startSyncing: $startSyncing")
        session.open()
        if (startSyncing) {
            session.startSyncing(context)
        }
        session.pushersService().refreshPushers()
        webRtcCallManager.checkForProtocolsSupportIfNeeded()
        updateMatrixClientInfoIfNeeded(session)
        createNotificationSettingsAccountDataIfNeeded(session)
        notificationsSettingUpdater.onSessionStarted(session)
        pushRulesUpdater.onSessionStarted(session)
    }

    private fun updateMatrixClientInfoIfNeeded(session: Session) {
        session.coroutineScope.launch {
            if (vectorPreferences.isClientInfoRecordingEnabled()) {
                updateMatrixClientInfoUseCase.execute(session)
            }
        }
    }

    private fun createNotificationSettingsAccountDataIfNeeded(session: Session) {
        session.coroutineScope.launch {
            updateNotificationSettingsAccountDataUseCase.execute(session)
        }
    }
}
