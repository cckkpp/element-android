/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.fvslnlqb.chat.BuildConfig
import com.fvslnlqb.chat.config.Analytics
import com.fvslnlqb.chat.config.Config
import com.fvslnlqb.chat.config.KeySharingStrategy
import com.fvslnlqb.chat.features.analytics.AnalyticsConfig
import com.fvslnlqb.chat.features.call.webrtc.VoipConfig
import com.fvslnlqb.chat.features.crypto.keysrequest.OutboundSessionKeySharingStrategy
import com.fvslnlqb.chat.features.home.room.detail.composer.voice.VoiceMessageConfig
import com.fvslnlqb.chat.features.location.LocationSharingConfig
import com.fvslnlqb.chat.features.raw.wellknown.CryptoConfig

@InstallIn(SingletonComponent::class)
@Module
object ConfigurationModule {

    @Provides
    fun providesAnalyticsConfig(): AnalyticsConfig {
        val config: Analytics = when (BuildConfig.BUILD_TYPE) {
            "debug" -> Config.DEBUG_ANALYTICS_CONFIG
            "nightly" -> Config.NIGHTLY_ANALYTICS_CONFIG
            "release" -> Config.RELEASE_ANALYTICS_CONFIG
            else -> throw IllegalStateException("Unhandled build type: ${BuildConfig.BUILD_TYPE}")
        }
        return when (config) {
            Analytics.Disabled -> AnalyticsConfig(isEnabled = false, "", "", "", "", "")
            is Analytics.Enabled -> AnalyticsConfig(
                    isEnabled = true,
                    postHogHost = config.postHogHost,
                    postHogApiKey = config.postHogApiKey,
                    policyLink = config.policyLink,
                    sentryDSN = config.sentryDSN,
                    sentryEnvironment = config.sentryEnvironment
            )
        }
    }

    @Provides
    fun providesVoiceMessageConfig() = VoiceMessageConfig(
            lengthLimitMs = Config.VOICE_MESSAGE_LIMIT_MS
    )

    @Provides
    fun providesCryptoConfig() = CryptoConfig(
            fallbackKeySharingStrategy = when (Config.KEY_SHARING_STRATEGY) {
                KeySharingStrategy.WhenSendingEvent -> OutboundSessionKeySharingStrategy.WhenSendingEvent
                KeySharingStrategy.WhenEnteringRoom -> OutboundSessionKeySharingStrategy.WhenEnteringRoom
                KeySharingStrategy.WhenTyping -> OutboundSessionKeySharingStrategy.WhenTyping
            }
    )

    @Provides
    fun providesLocationSharingConfig() = LocationSharingConfig(
            mapTilerKey = Config.LOCATION_MAP_TILER_KEY,
    )

    @Provides
    fun providesVoipConfig() = VoipConfig(
            handleCallAssertedIdentityEvents = Config.HANDLE_CALL_ASSERTED_IDENTITY_EVENTS
    )
}
