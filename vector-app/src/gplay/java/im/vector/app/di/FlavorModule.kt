/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.fvslnlqb.chat.GoogleFlavorLegals
import com.fvslnlqb.chat.core.pushers.FcmHelper
import com.fvslnlqb.chat.core.resources.AppNameProvider
import com.fvslnlqb.chat.core.resources.DefaultAppNameProvider
import com.fvslnlqb.chat.core.resources.DefaultLocaleProvider
import com.fvslnlqb.chat.core.resources.LocaleProvider
import com.fvslnlqb.chat.core.services.GuardServiceStarter
import com.fvslnlqb.chat.features.home.NightlyProxy
import com.fvslnlqb.chat.features.settings.legals.FlavorLegals
import com.fvslnlqb.chat.nightly.FirebaseNightlyProxy
import com.fvslnlqb.chat.push.fcm.GoogleFcmHelper

@InstallIn(SingletonComponent::class)
@Module
abstract class FlavorModule {

    companion object {
        @Provides
        fun provideGuardServiceStarter(): GuardServiceStarter {
            return object : GuardServiceStarter {}
        }
    }

    @Binds
    abstract fun bindsNightlyProxy(nightlyProxy: FirebaseNightlyProxy): NightlyProxy

    @Binds
    abstract fun bindsFcmHelper(fcmHelper: GoogleFcmHelper): FcmHelper

    @Binds
    abstract fun bindsLocaleProvider(localeProvider: DefaultLocaleProvider): LocaleProvider

    @Binds
    abstract fun bindsAppNameProvider(appNameProvider: DefaultAppNameProvider): AppNameProvider

    @Binds
    abstract fun bindsFlavorLegals(legals: GoogleFlavorLegals): FlavorLegals
}
