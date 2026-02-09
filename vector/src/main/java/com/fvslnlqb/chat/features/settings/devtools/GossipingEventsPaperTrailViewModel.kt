/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.settings.devtools

import androidx.lifecycle.asFlow
import androidx.paging.PagedList
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Uninitialized
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.fvslnlqb.chat.core.di.MavericksAssistedViewModelFactory
import com.fvslnlqb.chat.core.di.hiltMavericksViewModelFactory
import com.fvslnlqb.chat.core.platform.EmptyAction
import com.fvslnlqb.chat.core.platform.EmptyViewEvents
import com.fvslnlqb.chat.core.platform.VectorViewModel
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.crypto.model.AuditTrail

data class GossipingEventsPaperTrailState(
        val events: Async<PagedList<AuditTrail>> = Uninitialized
) : MavericksState

class GossipingEventsPaperTrailViewModel @AssistedInject constructor(
        @Assisted initialState: GossipingEventsPaperTrailState,
        private val session: Session
) :
        VectorViewModel<GossipingEventsPaperTrailState, EmptyAction, EmptyViewEvents>(initialState) {

    init {
        refresh()
    }

    fun refresh() {
        setState {
            copy(events = Loading())
        }
        if (session.cryptoService().supportKeyRequestInspection()) {
            session.cryptoService().getGossipingEventsTrail()
                    .asFlow()
                    .execute {
                        copy(events = it)
                    }
        }
    }

    override fun handle(action: EmptyAction) {}

    @AssistedFactory
    interface Factory : MavericksAssistedViewModelFactory<GossipingEventsPaperTrailViewModel, GossipingEventsPaperTrailState> {
        override fun create(initialState: GossipingEventsPaperTrailState): GossipingEventsPaperTrailViewModel
    }

    companion object : MavericksViewModelFactory<GossipingEventsPaperTrailViewModel, GossipingEventsPaperTrailState> by hiltMavericksViewModelFactory()
}
