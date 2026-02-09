/*
 * Copyright 2020-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.login

import com.fvslnlqb.chat.core.utils.TemporaryStore
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.minutes

/**
 * Will store the account password for 3 minutes.
 */
@Singleton
class ReAuthHelper @Inject constructor() : TemporaryStore<String>(3.minutes)
