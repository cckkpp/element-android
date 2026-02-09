/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.start

import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.core.di.ActiveSessionHolder
import com.fvslnlqb.chat.core.di.NamedGlobalScope
import com.fvslnlqb.chat.core.extensions.startForegroundCompat
import com.fvslnlqb.chat.core.services.VectorAndroidService
import com.fvslnlqb.chat.features.notifications.NotificationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

/**
 * A simple foreground service that let the app (and the SDK) time to initialize.
 * Will self stop itself once the active session is set.
 */
@AndroidEntryPoint
class StartAppAndroidService : VectorAndroidService() {

    @NamedGlobalScope @Inject lateinit var globalScope: CoroutineScope
    @Inject lateinit var notificationUtils: NotificationUtils
    @Inject lateinit var activeSessionHolder: ActiveSessionHolder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showStickyNotification()
        startPollingActiveSession()
        return START_STICKY
    }

    private fun startPollingActiveSession() {
        globalScope.launch {
            do {
                delay(1.seconds.inWholeMilliseconds)
            } while (activeSessionHolder.hasActiveSession().not())
            myStopSelf()
        }
    }

    private fun showStickyNotification() {
        val notificationId = Random.nextInt()
        val notification = notificationUtils.buildStartAppNotification()
        startForegroundCompat(notificationId, notification)
    }
}
