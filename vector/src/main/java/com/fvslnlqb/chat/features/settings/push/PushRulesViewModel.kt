/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */
package com.fvslnlqb.chat.features.settings.push

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import dagger.hilt.EntryPoints
import com.fvslnlqb.chat.core.di.SingletonEntryPoint
import com.fvslnlqb.chat.core.platform.EmptyAction
import com.fvslnlqb.chat.core.platform.EmptyViewEvents
import com.fvslnlqb.chat.core.platform.VectorViewModel
import org.matrix.android.sdk.api.session.pushrules.rest.PushRule

data class PushRulesViewState(
        val rules: List<PushRule> = emptyList()
) : MavericksState

class PushRulesViewModel(initialState: PushRulesViewState) :
        VectorViewModel<PushRulesViewState, EmptyAction, EmptyViewEvents>(initialState) {

    companion object : MavericksViewModelFactory<PushRulesViewModel, PushRulesViewState> {

        override fun initialState(viewModelContext: ViewModelContext): PushRulesViewState? {
            val session = EntryPoints.get(viewModelContext.app(), SingletonEntryPoint::class.java).activeSessionHolder().getActiveSession()
            val rules = session.pushRuleService().getPushRules().getAllRules()
            return PushRulesViewState(rules)
        }
    }

    override fun handle(action: EmptyAction) {
        // No op
    }
}
