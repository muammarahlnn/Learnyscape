package com.muammarahlnn.learnyscape.core.network.datasource.impl

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.muammarahlnn.learnyscape.core.network.api.UsersApi
import com.muammarahlnn.learnyscape.core.network.datasource.ProfileNetworkDataSource
import com.muammarahlnn.learnyscape.core.network.di.BASE_URL
import com.muammarahlnn.learnyscape.core.network.di.BEARER_TOKEN_AUTH
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
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

    override fun postProfilePic(pic: File): Flow<String> {
        val requestImageFile = pic.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val profilePicMultipart = MultipartBody.Part.createFormData(
            "pic",
            pic.name,
            requestImageFile
        )
        return flow {
            emit(usersApi.postProfilePic(profilePicMultipart).data)
        }
    }

    override fun getProfilePic(): Flow<Bitmap?> = flow {
        emit(usersApi.getProfilePic().toBitmap())
    }

    override fun getProfilePicByUrl(profilePicUrl: String): Flow<Bitmap?> = flow {
        val fullUrl = BASE_URL + profilePicUrl
        emit(usersApi.getProfilePicByUrl(fullUrl).toBitmap())
    }

    override fun getProfilePicById(userId: String): Flow<Bitmap?> = flow {
        emit(usersApi.getProfilePicById(userId).toBitmap())
    }

    private fun Response<ResponseBody>.toBitmap(): Bitmap? =
        if (this.isSuccessful) {
            val imageBytes = this.body()?.bytes() ?: byteArrayOf()
            imageBytes.toBitmap()
        } else null

    private fun ByteArray.toBitmap(): Bitmap =
        BitmapFactory.decodeByteArray(this, 0, this.size)
}