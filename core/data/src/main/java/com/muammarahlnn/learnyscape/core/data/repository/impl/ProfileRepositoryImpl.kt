package com.muammarahlnn.learnyscape.core.data.repository.impl

import android.graphics.Bitmap
import com.muammarahlnn.learnyscape.core.data.repository.ProfileRepository
import com.muammarahlnn.learnyscape.core.datastore.LearnyscapePreferencesDataSource
import com.muammarahlnn.learnyscape.core.network.datasource.ProfileNetworkDataSource
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileRepositoryImpl, 09/10/2023 19.18 by Muammar Ahlan Abimanyu
 */
class ProfileRepositoryImpl @Inject constructor(
    private val learnyscapePreferences: LearnyscapePreferencesDataSource,
    private val profileNetworkDataSource: ProfileNetworkDataSource,
) : ProfileRepository {

    override suspend fun logout() {
        learnyscapePreferences.removeUser()
    }

    override fun uploadProfilePic(pic: File): Flow<String> =
        profileNetworkDataSource.postProfilePic(pic)

    override fun getProfilePic(): Flow<Bitmap?> =
        profileNetworkDataSource.getProfilePic()

    override fun getProfilePicByUrl(profilePicUrl: String): Flow<Bitmap?> =
        profileNetworkDataSource.getProfilePicByUrl(profilePicUrl)
}