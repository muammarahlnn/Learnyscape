package com.muammarahlnn.learnyscape.core.domain.resourcedetails

import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File DeleteAnnouncementUseCase', 31/01/2024 16.15
 */
fun interface DeleteAnnouncementUseCase {

    operator fun invoke(announcementId: String): Flow<String>
}