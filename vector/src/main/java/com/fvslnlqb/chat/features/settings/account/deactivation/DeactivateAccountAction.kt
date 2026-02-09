/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.settings.account.deactivation

import com.fvslnlqb.chat.core.platform.VectorViewModelAction

sealed class DeactivateAccountAction : VectorViewModelAction {
    data class DeactivateAccount(val eraseAllData: Boolean) : DeactivateAccountAction()

    object SsoAuthDone : DeactivateAccountAction()
    data class PasswordAuthDone(val password: String) : DeactivateAccountAction()
    object ReAuthCancelled : DeactivateAccountAction()
}
