/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import com.fvslnlqb.chat.features.push.NotificationTroubleshootTestManagerFactory
import com.fvslnlqb.chat.push.fcm.FdroidNotificationTroubleshootTestManagerFactory

@InstallIn(ActivityComponent::class)
@Module
abstract class NotificationTestModule {
    @Binds
    abstract fun bindsNotificationTestFactory(factory: FdroidNotificationTroubleshootTestManagerFactory): NotificationTroubleshootTestManagerFactory
}
