/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.test.fakes

import com.fvslnlqb.chat.features.onboarding.RegisterAction
import com.fvslnlqb.chat.features.onboarding.RegistrationResult
import com.fvslnlqb.chat.features.onboarding.RegistrationWizardActionDelegate
import io.mockk.coEvery
import io.mockk.mockk

class FakeRegistrationWizardActionDelegate {

    val instance = mockk<RegistrationWizardActionDelegate>()

    fun givenResultsFor(result: List<Pair<RegisterAction, RegistrationResult>>) {
        coEvery { instance.executeAction(any()) } answers { call ->
            val actionArg = call.invocation.args[0] as RegisterAction
            result.first { it.first == actionArg }.second
        }
    }

    fun givenThrowsFor(action: RegisterAction, cause: Throwable) {
        coEvery { instance.executeAction(action) } throws cause
    }
}
