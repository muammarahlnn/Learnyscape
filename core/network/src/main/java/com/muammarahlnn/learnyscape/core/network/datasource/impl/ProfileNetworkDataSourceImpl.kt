package com.muammarahlnn.learnyscape.core.network.datasource.impl

import com.muammarahlnn.learnyscape.core.network.api.UsersApi
import com.muammarahlnn.learnyscape.core.network.datasource.ProfileNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.di.BEARER_TOKEN_AUTH
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileNetworkDataSourceImpl, 21/11/2023 19.07 by Muammar Ahlan Abimanyu
 */
@Singleton
class ProfileNetworkDataSourceImpl @Inject constructor(
    @Named(BEARER_TOKEN_AUTH) private val usersApi: UsersApi
) : ProfileNetworkDataSource {

    override fun putProfilePic(pic: File): Flow<String> {
        val requestImageFile = pic.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val profilePicMultipart = MultipartBody.Part.createFormData(
            "pic",
            pic.name,
            requestImageFile
        )
        return flow {
            emit(usersApi.putProfilePic(profilePicMultipart).data)
        }
    }
}