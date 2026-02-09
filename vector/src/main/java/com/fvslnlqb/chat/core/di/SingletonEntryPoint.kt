/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.core.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.fvslnlqb.chat.core.dialogs.UnrecognizedCertificateDialog
import com.fvslnlqb.chat.core.error.ErrorFormatter
import com.fvslnlqb.chat.features.analytics.AnalyticsTracker
import com.fvslnlqb.chat.features.call.webrtc.WebRtcCallManager
import com.fvslnlqb.chat.features.home.AvatarRenderer
import com.fvslnlqb.chat.features.navigation.Navigator
import com.fvslnlqb.chat.features.pin.PinLocker
import com.fvslnlqb.chat.features.rageshake.BugReporter
import com.fvslnlqb.chat.features.session.SessionListener
import com.fvslnlqb.chat.features.settings.VectorPreferences
import com.fvslnlqb.chat.features.ui.UiStateRepository
import im.vector.lib.core.utils.timer.Clock
import kotlinx.coroutines.CoroutineScope

@InstallIn(SingletonComponent::class)
@EntryPoint
interface SingletonEntryPoint {

    fun sessionListener(): SessionListener

    fun avatarRenderer(): AvatarRenderer

    fun activeSessionHolder(): ActiveSessionHolder

    fun unrecognizedCertificateDialog(): UnrecognizedCertificateDialog

    fun navigator(): Navigator

    fun clock(): Clock

    fun errorFormatter(): ErrorFormatter

    fun bugReporter(): BugReporter

    fun vectorPreferences(): VectorPreferences

    fun uiStateRepository(): UiStateRepository

    fun pinLocker(): PinLocker

    fun analyticsTracker(): AnalyticsTracker

    fun webRtcCallManager(): WebRtcCallManager

    fun appCoroutineScope(): CoroutineScope
}
