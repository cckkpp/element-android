/*
 * Copyright 2018-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */
package com.fvslnlqb.chat.push.fcm

import androidx.fragment.app.Fragment
import com.fvslnlqb.chat.core.pushers.UnifiedPushHelper
import com.fvslnlqb.chat.features.VectorFeatures
import com.fvslnlqb.chat.features.push.NotificationTroubleshootTestManagerFactory
import com.fvslnlqb.chat.features.settings.troubleshoot.NotificationTroubleshootTestManager
import com.fvslnlqb.chat.features.settings.troubleshoot.TestAccountSettings
import com.fvslnlqb.chat.features.settings.troubleshoot.TestAvailableUnifiedPushDistributors
import com.fvslnlqb.chat.features.settings.troubleshoot.TestCurrentUnifiedPushDistributor
import com.fvslnlqb.chat.features.settings.troubleshoot.TestDeviceSettings
import com.fvslnlqb.chat.features.settings.troubleshoot.TestEndpointAsTokenRegistration
import com.fvslnlqb.chat.features.settings.troubleshoot.TestNotification
import com.fvslnlqb.chat.features.settings.troubleshoot.TestPushFromPushGateway
import com.fvslnlqb.chat.features.settings.troubleshoot.TestPushRulesSettings
import com.fvslnlqb.chat.features.settings.troubleshoot.TestSystemSettings
import com.fvslnlqb.chat.features.settings.troubleshoot.TestUnifiedPushEndpoint
import com.fvslnlqb.chat.features.settings.troubleshoot.TestUnifiedPushGateway
import com.fvslnlqb.chat.gplay.features.settings.troubleshoot.TestFirebaseToken
import com.fvslnlqb.chat.gplay.features.settings.troubleshoot.TestPlayServices
import com.fvslnlqb.chat.gplay.features.settings.troubleshoot.TestTokenRegistration
import javax.inject.Inject

class GoogleNotificationTroubleshootTestManagerFactory @Inject constructor(
        private val unifiedPushHelper: UnifiedPushHelper,
        private val testSystemSettings: TestSystemSettings,
        private val testAccountSettings: TestAccountSettings,
        private val testDeviceSettings: TestDeviceSettings,
        private val testPushRulesSettings: TestPushRulesSettings,
        private val testPlayServices: TestPlayServices,
        private val testFirebaseToken: TestFirebaseToken,
        private val testTokenRegistration: TestTokenRegistration,
        private val testCurrentUnifiedPushDistributor: TestCurrentUnifiedPushDistributor,
        private val testUnifiedPushGateway: TestUnifiedPushGateway,
        private val testUnifiedPushEndpoint: TestUnifiedPushEndpoint,
        private val testAvailableUnifiedPushDistributors: TestAvailableUnifiedPushDistributors,
        private val testEndpointAsTokenRegistration: TestEndpointAsTokenRegistration,
        private val testPushFromPushGateway: TestPushFromPushGateway,
        private val testNotification: TestNotification,
        private val vectorFeatures: VectorFeatures,
) : NotificationTroubleshootTestManagerFactory {

    override fun create(fragment: Fragment): NotificationTroubleshootTestManager {
        val mgr = NotificationTroubleshootTestManager(fragment)
        mgr.addTest(testSystemSettings)
        mgr.addTest(testAccountSettings)
        mgr.addTest(testDeviceSettings)
        mgr.addTest(testPushRulesSettings)
        if (vectorFeatures.allowExternalUnifiedPushDistributors()) {
            mgr.addTest(testAvailableUnifiedPushDistributors)
            mgr.addTest(testCurrentUnifiedPushDistributor)
        }
        if (unifiedPushHelper.isEmbeddedDistributor()) {
            mgr.addTest(testPlayServices)
            mgr.addTest(testFirebaseToken)
            mgr.addTest(testTokenRegistration)
        } else {
            mgr.addTest(testUnifiedPushGateway)
            mgr.addTest(testUnifiedPushEndpoint)
            mgr.addTest(testEndpointAsTokenRegistration)
        }
        mgr.addTest(testPushFromPushGateway)
        mgr.addTest(testNotification)
        return mgr
    }
}
