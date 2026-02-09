/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.core.notification

import com.fvslnlqb.chat.features.session.coroutineScope
import com.fvslnlqb.chat.features.settings.notifications.usecase.UpdatePushRulesIfNeededUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.accountdata.UserAccountDataTypes
import org.matrix.android.sdk.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Listen changes in Account Data to update the push rules if needed.
 */
@Singleton
class PushRulesUpdater @Inject constructor(
        private val updatePushRulesIfNeededUseCase: UpdatePushRulesIfNeededUseCase,
) {

    private var job: Job? = null

    fun onSessionStarted(session: Session) {
        updatePushRulesOnChange(session)
    }

    private fun updatePushRulesOnChange(session: Session) {
        job?.cancel()
        job = session.coroutineScope.launch {
            session.flow()
                    .liveUserAccountData(UserAccountDataTypes.TYPE_PUSH_RULES)
                    .onEach { updatePushRulesIfNeededUseCase.execute(session) }
                    .collect()
        }
    }
}
