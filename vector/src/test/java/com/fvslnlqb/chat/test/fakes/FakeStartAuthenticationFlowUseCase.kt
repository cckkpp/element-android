/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.test.fakes

import com.fvslnlqb.chat.features.onboarding.StartAuthenticationFlowUseCase
import com.fvslnlqb.chat.features.onboarding.StartAuthenticationFlowUseCase.StartAuthenticationResult
import com.fvslnlqb.chat.test.fixtures.aHomeserverUnavailableError
import io.mockk.coEvery
import io.mockk.mockk
import org.matrix.android.sdk.api.auth.data.HomeServerConnectionConfig

class FakeStartAuthenticationFlowUseCase {

    val instance = mockk<StartAuthenticationFlowUseCase>()

    fun givenResult(config: HomeServerConnectionConfig, result: StartAuthenticationResult) {
        coEvery { instance.execute(config) } returns result
    }

    fun givenErrors(config: HomeServerConnectionConfig, error: Throwable) {
        coEvery { instance.execute(config) } throws  error
    }

    fun givenHomeserverUnavailable(config: HomeServerConnectionConfig) {
        coEvery { instance.execute(config) } throws aHomeserverUnavailableError()
    }
}
