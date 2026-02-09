/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.settings.notifications

import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import com.airbnb.mvrx.MavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.fvslnlqb.chat.core.di.MavericksAssistedViewModelFactory
import com.fvslnlqb.chat.core.di.hiltMavericksViewModelFactory
import com.fvslnlqb.chat.core.platform.VectorDummyViewState
import com.fvslnlqb.chat.core.platform.VectorViewModel
import com.fvslnlqb.chat.core.pushers.EnsureFcmTokenIsRetrievedUseCase
import com.fvslnlqb.chat.core.pushers.PushersManager
import com.fvslnlqb.chat.core.pushers.RegisterUnifiedPushUseCase
import com.fvslnlqb.chat.core.pushers.UnregisterUnifiedPushUseCase
import com.fvslnlqb.chat.features.settings.VectorPreferences
import com.fvslnlqb.chat.features.settings.notifications.usecase.DisableNotificationsForCurrentSessionUseCase
import com.fvslnlqb.chat.features.settings.notifications.usecase.EnableNotificationsForCurrentSessionUseCase
import com.fvslnlqb.chat.features.settings.notifications.usecase.ToggleNotificationsForCurrentSessionUseCase
import kotlinx.coroutines.launch

class VectorSettingsNotificationViewModel @AssistedInject constructor(
        @Assisted initialState: VectorDummyViewState,
        private val pushersManager: PushersManager,
        private val vectorPreferences: VectorPreferences,
        private val enableNotificationsForCurrentSessionUseCase: EnableNotificationsForCurrentSessionUseCase,
        private val disableNotificationsForCurrentSessionUseCase: DisableNotificationsForCurrentSessionUseCase,
        private val unregisterUnifiedPushUseCase: UnregisterUnifiedPushUseCase,
        private val registerUnifiedPushUseCase: RegisterUnifiedPushUseCase,
        private val ensureFcmTokenIsRetrievedUseCase: EnsureFcmTokenIsRetrievedUseCase,
        private val toggleNotificationsForCurrentSessionUseCase: ToggleNotificationsForCurrentSessionUseCase,
) : VectorViewModel<VectorDummyViewState, VectorSettingsNotificationViewAction, VectorSettingsNotificationViewEvent>(initialState) {

    @AssistedFactory
    interface Factory : MavericksAssistedViewModelFactory<VectorSettingsNotificationViewModel, VectorDummyViewState> {
        override fun create(initialState: VectorDummyViewState): VectorSettingsNotificationViewModel
    }

    companion object : MavericksViewModelFactory<VectorSettingsNotificationViewModel, VectorDummyViewState> by hiltMavericksViewModelFactory()

    @VisibleForTesting
    val notificationsPreferenceListener: SharedPreferences.OnSharedPreferenceChangeListener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                if (key == VectorPreferences.SETTINGS_ENABLE_THIS_DEVICE_PREFERENCE_KEY) {
                    if (vectorPreferences.areNotificationEnabledForDevice()) {
                        _viewEvents.post(VectorSettingsNotificationViewEvent.NotificationsForDeviceEnabled)
                    } else {
                        _viewEvents.post(VectorSettingsNotificationViewEvent.NotificationsForDeviceDisabled)
                    }
                }
            }

    init {
        observeNotificationsEnabledPreference()
    }

    private fun observeNotificationsEnabledPreference() {
        vectorPreferences.subscribeToChanges(notificationsPreferenceListener)
    }

    override fun onCleared() {
        vectorPreferences.unsubscribeToChanges(notificationsPreferenceListener)
        super.onCleared()
    }

    override fun handle(action: VectorSettingsNotificationViewAction) {
        when (action) {
            VectorSettingsNotificationViewAction.DisableNotificationsForDevice -> handleDisableNotificationsForDevice()
            is VectorSettingsNotificationViewAction.EnableNotificationsForDevice -> handleEnableNotificationsForDevice(action.pushDistributor)
            is VectorSettingsNotificationViewAction.RegisterPushDistributor -> handleRegisterPushDistributor(action.pushDistributor)
        }
    }

    private fun handleDisableNotificationsForDevice() {
        viewModelScope.launch {
            disableNotificationsForCurrentSessionUseCase.execute()
            _viewEvents.post(VectorSettingsNotificationViewEvent.NotificationsForDeviceDisabled)
        }
    }

    private fun handleEnableNotificationsForDevice(distributor: String) {
        viewModelScope.launch {
            when (enableNotificationsForCurrentSessionUseCase.execute(distributor)) {
                is EnableNotificationsForCurrentSessionUseCase.EnableNotificationsResult.NeedToAskUserForDistributor -> {
                    _viewEvents.post(VectorSettingsNotificationViewEvent.AskUserForPushDistributor)
                }
                EnableNotificationsForCurrentSessionUseCase.EnableNotificationsResult.Success -> {
                    _viewEvents.post(VectorSettingsNotificationViewEvent.NotificationsForDeviceEnabled)
                }
            }
        }
    }

    private fun handleRegisterPushDistributor(distributor: String) {
        viewModelScope.launch {
            unregisterUnifiedPushUseCase.execute(pushersManager)
            when (registerUnifiedPushUseCase.execute(distributor)) {
                RegisterUnifiedPushUseCase.RegisterUnifiedPushResult.NeedToAskUserForDistributor -> {
                    _viewEvents.post(VectorSettingsNotificationViewEvent.AskUserForPushDistributor)
                }
                RegisterUnifiedPushUseCase.RegisterUnifiedPushResult.Success -> {
                    val areNotificationsEnabled = vectorPreferences.areNotificationEnabledForDevice()
                    ensureFcmTokenIsRetrievedUseCase.execute(pushersManager, registerPusher = areNotificationsEnabled)
                    toggleNotificationsForCurrentSessionUseCase.execute(enabled = areNotificationsEnabled)
                    _viewEvents.post(VectorSettingsNotificationViewEvent.NotificationMethodChanged)
                }
            }
        }
    }
}
