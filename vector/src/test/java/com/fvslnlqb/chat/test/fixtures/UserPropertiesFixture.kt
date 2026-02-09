/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.test.fixtures

import com.fvslnlqb.chat.features.analytics.plan.UserProperties
import com.fvslnlqb.chat.features.analytics.plan.UserProperties.FtueUseCaseSelection

fun aUserProperties(
        ftueUseCase: FtueUseCaseSelection? = FtueUseCaseSelection.Skip
) = UserProperties(
        ftueUseCaseSelection = ftueUseCase
)
