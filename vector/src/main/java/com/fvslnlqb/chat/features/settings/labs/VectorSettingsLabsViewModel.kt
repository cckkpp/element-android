/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.settings.labs

import com.airbnb.mvrx.MavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.fvslnlqb.chat.core.di.ActiveSessionHolder
import com.fvslnlqb.chat.core.di.MavericksAssistedViewModelFactory
import com.fvslnlqb.chat.core.di.hiltMavericksViewModelFactory
import com.fvslnlqb.chat.core.platform.EmptyViewEvents
import com.fvslnlqb.chat.core.platform.VectorViewModel
import com.fvslnlqb.chat.core.session.clientinfo.DeleteMatrixClientInfoUseCase
import com.fvslnlqb.chat.core.session.clientinfo.UpdateMatrixClientInfoUseCase
import kotlinx.coroutines.launch

class VectorSettingsLabsViewModel @AssistedInject constructor(
        @Assisted initialState: VectorSettingsLabsViewState,
        private val activeSessionHolder: ActiveSessionHolder,
        private val updateMatrixClientInfoUseCase: UpdateMatrixClientInfoUseCase,
        private val deleteMatrixClientInfoUseCase: DeleteMatrixClientInfoUseCase,
) : VectorViewModel<VectorSettingsLabsViewState, VectorSettingsLabsAction, EmptyViewEvents>(initialState) {

    @AssistedFactory
    interface Factory : MavericksAssistedViewModelFactory<VectorSettingsLabsViewModel, VectorSettingsLabsViewState> {
        override fun create(initialState: VectorSettingsLabsViewState): VectorSettingsLabsViewModel
    }

    companion object : MavericksViewModelFactory<VectorSettingsLabsViewModel, VectorSettingsLabsViewState> by hiltMavericksViewModelFactory()

    override fun handle(action: VectorSettingsLabsAction) {
        when (action) {
            VectorSettingsLabsAction.UpdateClientInfo -> handleUpdateClientInfo()
            VectorSettingsLabsAction.DeleteRecordedClientInfo -> handleDeleteRecordedClientInfo()
        }
    }

    private fun handleUpdateClientInfo() {
        viewModelScope.launch {
            activeSessionHolder.getSafeActiveSession()
                    ?.let { session ->
                        updateMatrixClientInfoUseCase.execute(session)
                    }
        }
    }

    private fun handleDeleteRecordedClientInfo() {
        viewModelScope.launch {
            deleteMatrixClientInfoUseCase.execute()
        }
    }
}
