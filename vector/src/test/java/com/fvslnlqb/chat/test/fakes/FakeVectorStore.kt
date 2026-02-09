/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.test.fakes

import com.fvslnlqb.chat.features.onboarding.FtueUseCase
import com.fvslnlqb.chat.features.session.VectorSessionStore
import io.mockk.coEvery
import io.mockk.mockk

class FakeVectorStore {
    val instance = mockk<VectorSessionStore>()

    fun givenUseCase(useCase: FtueUseCase?) {
        coEvery {
            instance.readUseCase()
        } coAnswers {
            useCase
        }
    }
}
