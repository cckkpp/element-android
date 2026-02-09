/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.debug.features

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.core.extensions.cleanup
import com.fvslnlqb.chat.core.extensions.configureWith
import com.fvslnlqb.chat.core.platform.VectorBaseActivity
import com.fvslnlqb.chat.databinding.FragmentGenericRecyclerBinding
import javax.inject.Inject

@AndroidEntryPoint
class DebugFeaturesSettingsActivity : VectorBaseActivity<FragmentGenericRecyclerBinding>() {

    @Inject lateinit var debugFeatures: DebugVectorFeatures
    @Inject lateinit var debugFeaturesStateFactory: DebugFeaturesStateFactory
    @Inject lateinit var controller: FeaturesController

    override fun getBinding() = FragmentGenericRecyclerBinding.inflate(layoutInflater)

    override val rootView: View
        get() = views.mainRoot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller.listener = object : FeaturesController.Listener {
            override fun <T : Enum<T>> onEnumOptionSelected(option: T?, feature: Feature.EnumFeature<T>) {
                debugFeatures.overrideEnum(option, feature.type)
            }

            override fun onBooleanOptionSelected(option: Boolean?, feature: Feature.BooleanFeature) {
                debugFeatures.override(option, feature.key)
            }
        }
        views.genericRecyclerView.configureWith(controller)
        controller.setData(debugFeaturesStateFactory.create())
    }

    override fun onDestroy() {
        controller.listener = null
        views.genericRecyclerView.cleanup()
        super.onDestroy()
    }
}
