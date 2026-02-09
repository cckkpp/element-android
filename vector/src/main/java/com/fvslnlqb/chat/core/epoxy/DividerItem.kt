/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */
package com.fvslnlqb.chat.core.epoxy

import com.airbnb.epoxy.EpoxyModelClass
import com.fvslnlqb.chat.R

@EpoxyModelClass
abstract class DividerItem : VectorEpoxyModel<DividerItem.Holder>(R.layout.item_divider) {
    class Holder : VectorEpoxyHolder()
}
