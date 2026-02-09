/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.home

import com.airbnb.mvrx.MavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.fvslnlqb.chat.core.di.MavericksAssistedViewModelFactory
import com.fvslnlqb.chat.core.di.hiltMavericksViewModelFactory
import com.fvslnlqb.chat.core.platform.EmptyAction
import com.fvslnlqb.chat.core.platform.EmptyViewEvents
import com.fvslnlqb.chat.core.platform.VectorDummyViewState
import com.fvslnlqb.chat.core.platform.VectorViewModel
import com.fvslnlqb.chat.features.home.room.detail.timeline.helper.MatrixItemColorProvider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.accountdata.UserAccountDataTypes
import org.matrix.android.sdk.api.session.events.model.toModel
import org.matrix.android.sdk.flow.flow
import org.matrix.android.sdk.flow.unwrap
import timber.log.Timber

class UserColorAccountDataViewModel @AssistedInject constructor(
        @Assisted initialState: VectorDummyViewState,
        private val session: Session,
        private val matrixItemColorProvider: MatrixItemColorProvider
) : VectorViewModel<VectorDummyViewState, EmptyAction, EmptyViewEvents>(initialState) {

    @AssistedFactory
    interface Factory : MavericksAssistedViewModelFactory<UserColorAccountDataViewModel, VectorDummyViewState> {
        override fun create(initialState: VectorDummyViewState): UserColorAccountDataViewModel
    }

    companion object : MavericksViewModelFactory<UserColorAccountDataViewModel, VectorDummyViewState> by hiltMavericksViewModelFactory()

    init {
        observeAccountData()
    }

    private fun observeAccountData() {
        session.flow()
                .liveUserAccountData(UserAccountDataTypes.TYPE_OVERRIDE_COLORS)
                .unwrap()
                .map { it.content.toModel<Map<String, String>>() }
                .onEach { userColorAccountDataContent ->
                    if (userColorAccountDataContent == null) {
                        Timber.w("Invalid account data im.vector.setting.override_colors")
                    }
                    matrixItemColorProvider.setOverrideColors(userColorAccountDataContent)
                }
                .launchIn(viewModelScope)
    }

    override fun handle(action: EmptyAction) {
        // No op
    }
}
