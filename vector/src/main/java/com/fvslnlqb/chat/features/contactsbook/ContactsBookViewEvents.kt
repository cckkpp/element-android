/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.contactsbook

import com.fvslnlqb.chat.core.platform.VectorViewEvents
import com.fvslnlqb.chat.features.discovery.ServerAndPolicies

sealed class ContactsBookViewEvents : VectorViewEvents {
    data class Failure(val throwable: Throwable) : ContactsBookViewEvents()
    data class OnPoliciesRetrieved(val identityServerWithTerms: ServerAndPolicies?) : ContactsBookViewEvents()
}
