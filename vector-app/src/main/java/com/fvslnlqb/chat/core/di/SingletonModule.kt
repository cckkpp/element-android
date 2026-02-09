/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.core.di

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.preference.PreferenceManager
import com.google.i18n.phonenumbers.PhoneNumberUtil
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.fvslnlqb.chat.EmojiCompatWrapper
import com.fvslnlqb.chat.EmojiSpanify
import com.fvslnlqb.chat.SpaceStateHandler
import com.fvslnlqb.chat.SpaceStateHandlerImpl
import com.fvslnlqb.chat.config.Config
import com.fvslnlqb.chat.core.device.DefaultGetDeviceInfoUseCase
import com.fvslnlqb.chat.core.device.GetDeviceInfoUseCase
import com.fvslnlqb.chat.core.dispatchers.CoroutineDispatchers
import com.fvslnlqb.chat.core.error.DefaultErrorFormatter
import com.fvslnlqb.chat.core.error.ErrorFormatter
import com.fvslnlqb.chat.core.resources.BuildMeta
import com.fvslnlqb.chat.core.utils.AndroidSystemSettingsProvider
import com.fvslnlqb.chat.core.utils.SystemSettingsProvider
import com.fvslnlqb.chat.features.analytics.AnalyticsTracker
import com.fvslnlqb.chat.features.analytics.VectorAnalytics
import com.fvslnlqb.chat.features.analytics.errors.ErrorTracker
import com.fvslnlqb.chat.features.analytics.impl.DefaultVectorAnalytics
import com.fvslnlqb.chat.features.analytics.metrics.VectorPlugins
import com.fvslnlqb.chat.features.configuration.VectorCustomEventTypesProvider
import com.fvslnlqb.chat.features.invite.AutoAcceptInvites
import com.fvslnlqb.chat.features.invite.CompileTimeAutoAcceptInvites
import com.fvslnlqb.chat.features.mdm.DefaultMdmService
import com.fvslnlqb.chat.features.mdm.MdmData
import com.fvslnlqb.chat.features.mdm.MdmService
import com.fvslnlqb.chat.features.navigation.DefaultNavigator
import com.fvslnlqb.chat.features.navigation.Navigator
import com.fvslnlqb.chat.features.pin.PinCodeStore
import com.fvslnlqb.chat.features.pin.SharedPrefPinCodeStore
import com.fvslnlqb.chat.features.room.VectorRoomDisplayNameFallbackProvider
import com.fvslnlqb.chat.features.settings.FontScalePreferences
import com.fvslnlqb.chat.features.settings.FontScalePreferencesImpl
import com.fvslnlqb.chat.features.settings.VectorPreferences
import com.fvslnlqb.chat.features.ui.SharedPreferencesUiStateRepository
import com.fvslnlqb.chat.features.ui.UiStateRepository
import com.fvslnlqb.chat.BuildConfig
import com.fvslnlqb.chat.R
import im.vector.lib.core.utils.timer.Clock
import im.vector.lib.core.utils.timer.DefaultClock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import org.matrix.android.sdk.api.Matrix
import org.matrix.android.sdk.api.MatrixConfiguration
import org.matrix.android.sdk.api.SyncConfig
import org.matrix.android.sdk.api.auth.AuthenticationService
import org.matrix.android.sdk.api.auth.HomeServerHistoryService
import org.matrix.android.sdk.api.raw.RawService
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.sync.filter.SyncFilterParams
import org.matrix.android.sdk.api.settings.LightweightSettingsStorage
import javax.inject.Singleton

@InstallIn(SingletonComponent::class) @Module abstract class VectorBindModule {

    @Binds
    abstract fun bindNavigator(navigator: DefaultNavigator): Navigator

    @Binds
    abstract fun bindVectorAnalytics(analytics: DefaultVectorAnalytics): VectorAnalytics

    @Binds
    abstract fun bindErrorTracker(analytics: DefaultVectorAnalytics): ErrorTracker

    @Binds
    abstract fun bindAnalyticsTracker(analytics: DefaultVectorAnalytics): AnalyticsTracker

    @Binds
    abstract fun bindErrorFormatter(formatter: DefaultErrorFormatter): ErrorFormatter

    @Binds
    abstract fun bindUiStateRepository(repository: SharedPreferencesUiStateRepository): UiStateRepository

    @Binds
    abstract fun bindPinCodeStore(store: SharedPrefPinCodeStore): PinCodeStore

    @Binds
    abstract fun bindAutoAcceptInvites(autoAcceptInvites: CompileTimeAutoAcceptInvites): AutoAcceptInvites

    @Binds
    abstract fun bindEmojiSpanify(emojiCompatWrapper: EmojiCompatWrapper): EmojiSpanify

    @Binds
    abstract fun bindMdmService(service: DefaultMdmService): MdmService

    @Binds
    abstract fun bindFontScale(fontScale: FontScalePreferencesImpl): FontScalePreferences

    @Binds
    abstract fun bindSystemSettingsProvide(provider: AndroidSystemSettingsProvider): SystemSettingsProvider

    @Binds
    abstract fun bindSpaceStateHandler(spaceStateHandlerImpl: SpaceStateHandlerImpl): SpaceStateHandler

    @Binds
    abstract fun bindGetDeviceInfoUseCase(getDeviceInfoUseCase: DefaultGetDeviceInfoUseCase): GetDeviceInfoUseCase
}

@InstallIn(SingletonComponent::class) @Module object VectorStaticModule {

    @Provides
    fun providesContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun providesResources(context: Context): Resources {
        return context.resources
    }

    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("im.vector.riot", MODE_PRIVATE)
    }

    @Provides
    fun providesMatrixConfiguration(
            vectorPreferences: VectorPreferences,
            vectorRoomDisplayNameFallbackProvider: VectorRoomDisplayNameFallbackProvider,
            vectorPlugins: VectorPlugins,
            vectorCustomEventTypesProvider: VectorCustomEventTypesProvider,
            mdmService: MdmService,
    ): MatrixConfiguration {
        return MatrixConfiguration(
                applicationFlavor = BuildConfig.FLAVOR_DESCRIPTION,
                roomDisplayNameFallbackProvider = vectorRoomDisplayNameFallbackProvider,
                threadMessagesEnabledDefault = vectorPreferences.areThreadMessagesEnabled(),
                networkInterceptors = emptyList(),
                metricPlugins = vectorPlugins.plugins(),
                cryptoAnalyticsPlugin = vectorPlugins.cryptoMetricPlugin,
                customEventTypesProvider = vectorCustomEventTypesProvider,
                clientPermalinkBaseUrl = mdmService.getData(MdmData.PermalinkBaseUrl),
                syncConfig = SyncConfig(
                        syncFilterParams = SyncFilterParams(lazyLoadMembersForStateEvents = true, useThreadNotifications = true)
                )
        )
    }

    @Provides
    @Singleton
    fun providesMatrix(context: Context, configuration: MatrixConfiguration): Matrix {
        return Matrix(context, configuration)
    }

    @Provides
    fun providesCurrentSession(activeSessionHolder: ActiveSessionHolder): Session {
        // TODO handle session injection better
        return activeSessionHolder.getActiveSession()
    }

    @Provides
    fun providesAuthenticationService(matrix: Matrix): AuthenticationService {
        return matrix.authenticationService()
    }

    @Provides
    fun providesRawService(matrix: Matrix): RawService {
        return matrix.rawService()
    }

    @Provides
    fun providesLightweightSettingsStorage(matrix: Matrix): LightweightSettingsStorage {
        return matrix.lightweightSettingsStorage()
    }

    @Provides
    fun providesHomeServerHistoryService(matrix: Matrix): HomeServerHistoryService {
        return matrix.homeServerHistoryService()
    }

    @Provides
    @Singleton
    fun providesApplicationCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }

    @Provides
    fun providesCoroutineDispatchers(): CoroutineDispatchers {
        return CoroutineDispatchers(io = Dispatchers.IO, computation = Dispatchers.Default)
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Provides
    @NamedGlobalScope
    fun providesGlobalScope(): CoroutineScope {
        return GlobalScope
    }

    @Provides
    fun providesPhoneNumberUtil(): PhoneNumberUtil = PhoneNumberUtil.getInstance()

    @Provides
    @Singleton
    fun providesBuildMeta(context: Context) = BuildMeta(
            isDebug = BuildConfig.DEBUG,
            applicationId = BuildConfig.APPLICATION_ID,
            applicationName = context.getString(R.string.app_name),
            lowPrivacyLoggingEnabled = Config.LOW_PRIVACY_LOG_ENABLE,
            versionName = BuildConfig.VERSION_NAME,
            gitRevision = BuildConfig.GIT_REVISION,
            gitRevisionDate = BuildConfig.GIT_REVISION_DATE,
            gitBranchName = BuildConfig.GIT_BRANCH_NAME,
            flavorDescription = BuildConfig.FLAVOR_DESCRIPTION,
            flavorShortDescription = BuildConfig.SHORT_FLAVOR_DESCRIPTION,
    )

    @Provides
    @Singleton
    @DefaultPreferences
    fun providesDefaultSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
    }

    @Singleton
    @Provides
    fun providesDefaultClock(): Clock = DefaultClock()
}
