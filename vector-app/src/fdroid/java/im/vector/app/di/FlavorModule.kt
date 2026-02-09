/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.fvslnlqb.chat.core.pushers.FcmHelper
import com.fvslnlqb.chat.core.resources.AppNameProvider
import com.fvslnlqb.chat.core.resources.DefaultAppNameProvider
import com.fvslnlqb.chat.core.resources.DefaultLocaleProvider
import com.fvslnlqb.chat.core.resources.LocaleProvider
import com.fvslnlqb.chat.core.services.GuardServiceStarter
import com.fvslnlqb.chat.fdroid.service.FDroidGuardServiceStarter
import com.fvslnlqb.chat.features.home.NightlyProxy
import com.fvslnlqb.chat.features.settings.VectorPreferences
import com.fvslnlqb.chat.features.settings.legals.FlavorLegals
import com.fvslnlqb.chat.push.fcm.FdroidFcmHelper

@InstallIn(SingletonComponent::class)
@Module
abstract class FlavorModule {

    companion object {
        @Provides
        fun provideGuardServiceStarter(preferences: VectorPreferences, appContext: Context): GuardServiceStarter {
            return FDroidGuardServiceStarter(preferences, appContext)
        }

        @Provides
        fun provideNightlyProxy() = object : NightlyProxy {
            override fun canDisplayPopup() = false
            override fun isNightlyBuild() = false
            override fun updateApplication() = Unit
        }

        @Provides
        fun providesFlavorLegals() = object : FlavorLegals {
            override fun hasThirdPartyNotices() = false

            override fun navigateToThirdPartyNotices(context: Context) {
                // no op
            }
        }
    }

    @Binds
    abstract fun bindsFcmHelper(fcmHelper: FdroidFcmHelper): FcmHelper

    @Binds
    abstract fun bindsLocaleProvider(localeProvider: DefaultLocaleProvider): LocaleProvider

    @Binds
    abstract fun bindsAppNameProvider(appNameProvider: DefaultAppNameProvider): AppNameProvider
}
