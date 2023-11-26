package com.muammarahlnn.learnyscape.core.network.datasource

import kotlinx.coroutines.flow.Flow
import java.io.File


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProfileNetworkDataSource, 21/11/2023 19.07 by Muammar Ahlan Abimanyu
 */
interface ProfileNetworkDataSource {

    fun putProfilePic(pic: File): Flow<String>
}