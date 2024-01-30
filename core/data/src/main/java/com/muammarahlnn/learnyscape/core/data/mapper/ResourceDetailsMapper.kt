package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.data.util.formatEpochSeconds
import com.muammarahlnn.learnyscape.core.data.util.formatIsoDate
import com.muammarahlnn.learnyscape.core.model.data.AssignmentDetailsModel
import com.muammarahlnn.learnyscape.core.model.data.ModuleDetailsModel
import com.muammarahlnn.learnyscape.core.network.model.response.ReferenceDetailsResponse
import com.muammarahlnn.learnyscape.core.network.model.response.TaskDetailsResponse
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsMapper, 18/01/2024 17.45
 */
fun ReferenceDetailsResponse.toModuleDetailsModel(attachments: List<File>) = ModuleDetailsModel(
    id = id,
    name = name,
    description = description.orEmpty(),
    updatedAt = formatIsoDate(updatedAt),
    attachments = attachments,
)

fun TaskDetailsResponse.toAssignmentDetailsModel(attachments: List<File>) = AssignmentDetailsModel(
    id = id,
    name = name,
    description = description.orEmpty(),
    updatedAt = formatIsoDate(updatedAt),
    dueDate = formatEpochSeconds(dueDate),
    attachments = attachments,
)