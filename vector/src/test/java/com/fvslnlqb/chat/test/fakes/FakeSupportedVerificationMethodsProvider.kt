/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.test.fakes

import com.fvslnlqb.chat.features.crypto.verification.SupportedVerificationMethodsProvider
import io.mockk.mockk

class FakeSupportedVerificationMethodsProvider {

    val instance = mockk<SupportedVerificationMethodsProvider>()
}
