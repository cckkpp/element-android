/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.rageshake

import com.airbnb.mvrx.MavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.fvslnlqb.chat.core.di.ActiveSessionHolder
import com.fvslnlqb.chat.core.di.MavericksAssistedViewModelFactory
import com.fvslnlqb.chat.core.di.hiltMavericksViewModelFactory
import com.fvslnlqb.chat.core.platform.EmptyAction
import com.fvslnlqb.chat.core.platform.EmptyViewEvents
import com.fvslnlqb.chat.core.platform.VectorViewModel
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.extensions.tryOrNull

class BugReportViewModel @AssistedInject constructor(
        @Assisted initialState: BugReportState,
        val activeSessionHolder: ActiveSessionHolder
) : VectorViewModel<BugReportState, EmptyAction, EmptyViewEvents>(initialState) {

    @AssistedFactory
    interface Factory : MavericksAssistedViewModelFactory<BugReportViewModel, BugReportState> {
        override fun create(initialState: BugReportState): BugReportViewModel
    }

    companion object : MavericksViewModelFactory<BugReportViewModel, BugReportState> by hiltMavericksViewModelFactory()

    init {
        fetchHomeserverVersion()
    }

    private fun fetchHomeserverVersion() {
        viewModelScope.launch {
            val version = tryOrNull {
                activeSessionHolder.getSafeActiveSession()
                        ?.federationService()
                        ?.getFederationVersion()
                        ?.let { "${it.name} - ${it.version}" }
            } ?: "undefined"

            setState {
                copy(
                        serverVersion = version
                )
            }
        }
    }

    override fun handle(action: EmptyAction) {
        // No op
    }
}
