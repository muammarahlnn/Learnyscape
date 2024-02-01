package com.muammarahlnn.learnyscape.core.network.datasource

import android.graphics.Bitmap
import com.muammarahlnn.learnyscape.core.network.model.response.UploadProfilePicResponse
import kotlinx.coroutines.flow.Flow
import java.io.File


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileNetworkDataSource, 21/11/2023 19.07 by Muammar Ahlan Abimanyu
 */
interface ProfileNetworkDataSource {

    fun postProfilePic(pic: File): Flow<UploadProfilePicResponse>

    fun getProfilePic(): Flow<Bitmap?>

    fun getProfilePicByUrl(profilePicUrl: String): Flow<Bitmap?>

    fun getProfilePicById(userId: String): Flow<Bitmap?>
}