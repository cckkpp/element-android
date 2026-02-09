/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.debug.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap
import com.fvslnlqb.chat.core.di.MavericksAssistedViewModelFactory
import com.fvslnlqb.chat.core.di.MavericksViewModelComponent
import com.fvslnlqb.chat.core.di.MavericksViewModelKey
import com.fvslnlqb.chat.features.debug.analytics.DebugAnalyticsViewModel
import com.fvslnlqb.chat.features.debug.leak.DebugMemoryLeaksViewModel
import com.fvslnlqb.chat.features.debug.settings.DebugPrivateSettingsViewModel

@InstallIn(MavericksViewModelComponent::class)
@Module
interface MavericksViewModelDebugModule {

    @Binds
    @IntoMap
    @MavericksViewModelKey(DebugAnalyticsViewModel::class)
    fun debugAnalyticsViewModelFactory(factory: DebugAnalyticsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(DebugPrivateSettingsViewModel::class)
    fun debugPrivateSettingsViewModelFactory(factory: DebugPrivateSettingsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(DebugMemoryLeaksViewModel::class)
    fun debugMemoryLeaksViewModelFactory(factory: DebugMemoryLeaksViewModel.Factory): MavericksAssistedViewModelFactory<*, *>
}
