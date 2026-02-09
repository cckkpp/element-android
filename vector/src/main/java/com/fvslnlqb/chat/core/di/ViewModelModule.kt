/*
 * Copyright 2019-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package com.fvslnlqb.chat.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap
import com.fvslnlqb.chat.core.platform.ConfigurationViewModel
import com.fvslnlqb.chat.features.call.SharedKnownCallsViewModel
import com.fvslnlqb.chat.features.crypto.keysbackup.restore.KeysBackupRestoreFromKeyViewModel
import com.fvslnlqb.chat.features.crypto.keysbackup.restore.KeysBackupRestoreFromPassphraseViewModel
import com.fvslnlqb.chat.features.crypto.keysbackup.restore.KeysBackupRestoreSharedViewModel
import com.fvslnlqb.chat.features.crypto.keysbackup.setup.KeysBackupSetupSharedViewModel
import com.fvslnlqb.chat.features.discovery.DiscoverySharedViewModel
import com.fvslnlqb.chat.features.home.HomeSharedActionViewModel
import com.fvslnlqb.chat.features.home.room.detail.RoomDetailSharedActionViewModel
import com.fvslnlqb.chat.features.home.room.detail.timeline.action.MessageSharedActionViewModel
import com.fvslnlqb.chat.features.home.room.list.actions.RoomListQuickActionsSharedActionViewModel
import com.fvslnlqb.chat.features.home.room.list.actions.RoomListSharedActionViewModel
import com.fvslnlqb.chat.features.reactions.EmojiChooserViewModel
import com.fvslnlqb.chat.features.roomdirectory.RoomDirectorySharedActionViewModel
import com.fvslnlqb.chat.features.roomprofile.RoomProfileSharedActionViewModel
import com.fvslnlqb.chat.features.roomprofile.alias.detail.RoomAliasBottomSheetSharedActionViewModel
import com.fvslnlqb.chat.features.roomprofile.settings.historyvisibility.RoomHistoryVisibilitySharedActionViewModel
import com.fvslnlqb.chat.features.roomprofile.settings.joinrule.RoomJoinRuleSharedActionViewModel
import com.fvslnlqb.chat.features.spaces.SpacePreviewSharedActionViewModel
import com.fvslnlqb.chat.features.spaces.people.SpacePeopleSharedActionViewModel
import com.fvslnlqb.chat.features.userdirectory.UserListSharedActionViewModel

@InstallIn(ActivityComponent::class)
@Module
interface ViewModelModule {

    /**
     * ViewModels with @IntoMap will be injected by this factory.
     */
    @Binds
    fun bindViewModelFactory(factory: VectorViewModelFactory): ViewModelProvider.Factory

    /**
     *  Below are bindings for the androidx view models (which extend ViewModel). Will be converted to MvRx ViewModel in the future.
     */

    @Binds
    @IntoMap
    @ViewModelKey(EmojiChooserViewModel::class)
    fun bindEmojiChooserViewModel(viewModel: EmojiChooserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(KeysBackupRestoreFromKeyViewModel::class)
    fun bindKeysBackupRestoreFromKeyViewModel(viewModel: KeysBackupRestoreFromKeyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(KeysBackupRestoreSharedViewModel::class)
    fun bindKeysBackupRestoreSharedViewModel(viewModel: KeysBackupRestoreSharedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(KeysBackupRestoreFromPassphraseViewModel::class)
    fun bindKeysBackupRestoreFromPassphraseViewModel(viewModel: KeysBackupRestoreFromPassphraseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(KeysBackupSetupSharedViewModel::class)
    fun bindKeysBackupSetupSharedViewModel(viewModel: KeysBackupSetupSharedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConfigurationViewModel::class)
    fun bindConfigurationViewModel(viewModel: ConfigurationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SharedKnownCallsViewModel::class)
    fun bindSharedActiveCallViewModel(viewModel: SharedKnownCallsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserListSharedActionViewModel::class)
    fun bindUserListSharedActionViewModel(viewModel: UserListSharedActionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeSharedActionViewModel::class)
    fun bindHomeSharedActionViewModel(viewModel: HomeSharedActionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MessageSharedActionViewModel::class)
    fun bindMessageSharedActionViewModel(viewModel: MessageSharedActionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomListQuickActionsSharedActionViewModel::class)
    fun bindRoomListQuickActionsSharedActionViewModel(viewModel: RoomListQuickActionsSharedActionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomAliasBottomSheetSharedActionViewModel::class)
    fun bindRoomAliasBottomSheetSharedActionViewModel(viewModel: RoomAliasBottomSheetSharedActionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomHistoryVisibilitySharedActionViewModel::class)
    fun bindRoomHistoryVisibilitySharedActionViewModel(viewModel: RoomHistoryVisibilitySharedActionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomJoinRuleSharedActionViewModel::class)
    fun bindRoomJoinRuleSharedActionViewModel(viewModel: RoomJoinRuleSharedActionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomDirectorySharedActionViewModel::class)
    fun bindRoomDirectorySharedActionViewModel(viewModel: RoomDirectorySharedActionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomDetailSharedActionViewModel::class)
    fun bindRoomDetailSharedActionViewModel(viewModel: RoomDetailSharedActionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomProfileSharedActionViewModel::class)
    fun bindRoomProfileSharedActionViewModel(viewModel: RoomProfileSharedActionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DiscoverySharedViewModel::class)
    fun bindDiscoverySharedViewModel(viewModel: DiscoverySharedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SpacePreviewSharedActionViewModel::class)
    fun bindSpacePreviewSharedActionViewModel(viewModel: SpacePreviewSharedActionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SpacePeopleSharedActionViewModel::class)
    fun bindSpacePeopleSharedActionViewModel(viewModel: SpacePeopleSharedActionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomListSharedActionViewModel::class)
    fun bindRoomListSharedActionViewModel(viewModel: RoomListSharedActionViewModel): ViewModel
}
