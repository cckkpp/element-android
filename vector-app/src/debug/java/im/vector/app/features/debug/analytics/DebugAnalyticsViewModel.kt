/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.debug.analytics

import com.airbnb.mvrx.MavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.fvslnlqb.chat.core.di.MavericksAssistedViewModelFactory
import com.fvslnlqb.chat.core.di.hiltMavericksViewModelFactory
import com.fvslnlqb.chat.core.platform.EmptyViewEvents
import com.fvslnlqb.chat.core.platform.VectorViewModel
import com.fvslnlqb.chat.features.analytics.store.AnalyticsStore
import kotlinx.coroutines.launch

class DebugAnalyticsViewModel @AssistedInject constructor(
        @Assisted initialState: DebugAnalyticsViewState,
        private val analyticsStore: AnalyticsStore
) : VectorViewModel<DebugAnalyticsViewState, DebugAnalyticsViewActions, EmptyViewEvents>(initialState) {

    @AssistedFactory
    interface Factory : MavericksAssistedViewModelFactory<DebugAnalyticsViewModel, DebugAnalyticsViewState> {
        override fun create(initialState: DebugAnalyticsViewState): DebugAnalyticsViewModel
    }

    companion object : MavericksViewModelFactory<DebugAnalyticsViewModel, DebugAnalyticsViewState> by hiltMavericksViewModelFactory()

    init {
        observerStore()
    }

    private fun observerStore() {
        analyticsStore.analyticsIdFlow.setOnEach { copy(analyticsId = it) }
        analyticsStore.userConsentFlow.setOnEach { copy(userConsent = it) }
        analyticsStore.didAskUserConsentFlow.setOnEach { copy(didAskUserConsent = it) }
    }

    override fun handle(action: DebugAnalyticsViewActions) {
        when (action) {
            DebugAnalyticsViewActions.ResetAnalyticsOptInDisplayed -> handleResetAnalyticsOptInDisplayed()
        }
    }

    private fun handleResetAnalyticsOptInDisplayed() {
        viewModelScope.launch {
            analyticsStore.setDidAskUserConsent(false)
        }
    }
}
