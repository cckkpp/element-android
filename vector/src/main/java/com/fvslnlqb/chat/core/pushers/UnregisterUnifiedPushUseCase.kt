/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.core.pushers

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import com.fvslnlqb.chat.features.settings.BackgroundSyncMode
import com.fvslnlqb.chat.features.settings.VectorPreferences
import org.unifiedpush.android.connector.UnifiedPush
import timber.log.Timber
import javax.inject.Inject

class UnregisterUnifiedPushUseCase @Inject constructor(
        @ApplicationContext private val context: Context,
        private val vectorPreferences: VectorPreferences,
        private val unifiedPushStore: UnifiedPushStore,
        private val unifiedPushHelper: UnifiedPushHelper,
) {

    suspend fun execute(pushersManager: PushersManager?) {
        val mode = BackgroundSyncMode.FDROID_BACKGROUND_SYNC_MODE_FOR_REALTIME
        vectorPreferences.setFdroidSyncBackgroundMode(mode)
        try {
            unifiedPushHelper.getEndpointOrToken()?.let {
                Timber.d("Removing $it")
                pushersManager?.unregisterPusher(it)
            }
        } catch (e: Exception) {
            Timber.d(e, "Probably unregistering a non existing pusher")
        }
        unifiedPushStore.storeUpEndpoint(null)
        unifiedPushStore.storePushGateway(null)
        UnifiedPush.unregisterApp(context)
    }
}
