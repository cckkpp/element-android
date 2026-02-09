/*
 * Copyright 2021-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.features.onboarding

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import com.fvslnlqb.chat.core.extensions.lazyViewModel
import com.fvslnlqb.chat.core.extensions.validateBackPressed
import com.fvslnlqb.chat.core.platform.VectorBaseActivity
import com.fvslnlqb.chat.core.platform.lifecycleAwareLazy
import com.fvslnlqb.chat.databinding.ActivityLoginBinding
import com.fvslnlqb.chat.features.login.LoginConfig
import com.fvslnlqb.chat.features.pin.UnlockedActivity
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingActivity : VectorBaseActivity<ActivityLoginBinding>(), UnlockedActivity {

    private val onboardingVariant by lifecycleAwareLazy {
        onboardingVariantFactory.create(this, views = views, onboardingViewModel = lazyViewModel())
    }

    @Inject lateinit var onboardingVariantFactory: OnboardingVariantFactory

    override fun getBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun getCoordinatorLayout() = views.coordinatorLayout

    override val rootView: View
        get() = views.coordinatorLayout

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        onboardingVariant.onNewIntent(intent)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun onBackPressed() {
        validateBackPressed {
            super.onBackPressed()
        }
    }

    override fun initUiAndData() {
        onboardingVariant.initUiAndData(isFirstCreation())
    }

    // Hack for AccountCreatedFragment
    fun setIsLoading(isLoading: Boolean) {
        onboardingVariant.setIsLoading(isLoading)
    }

    companion object {
        const val EXTRA_CONFIG = "EXTRA_CONFIG"

        fun newIntent(context: Context, loginConfig: LoginConfig?): Intent {
            return Intent(context, OnboardingActivity::class.java).apply {
                putExtra(EXTRA_CONFIG, loginConfig)
            }
        }

        fun redirectIntent(context: Context, data: Uri?): Intent {
            return Intent(context, OnboardingActivity::class.java).apply {
                setData(data)
            }
        }
    }
}
