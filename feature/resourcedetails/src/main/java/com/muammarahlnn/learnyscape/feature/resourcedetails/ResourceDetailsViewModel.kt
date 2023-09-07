package com.muammarahlnn.learnyscape.feature.resourcedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.feature.resourcedetails.navigation.ResourceDetailsArgs


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ResourceDetailsViewModel, 21/08/2023 21.10 by Muammar Ahlan Abimanyu
 */
class ResourceDetailsViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val resourceDetailsArgs = ResourceDetailsArgs(savedStateHandle)

    val classResourceType = getClassResourceType(resourceDetailsArgs.resourceTypeOrdinal)

    private fun getClassResourceType(classResourceTypeOrdinal: Int) =
        when (classResourceTypeOrdinal) {
            ClassResourceType.ANNOUNCEMENT.ordinal -> ClassResourceType.ANNOUNCEMENT
            ClassResourceType.MODULE.ordinal -> ClassResourceType.MODULE
            ClassResourceType.ASSIGNMENT.ordinal -> ClassResourceType.ASSIGNMENT
            ClassResourceType.QUIZ.ordinal -> ClassResourceType.QUIZ
            else -> throw IllegalStateException("The given ordinal not matched any ClassResourceType ordinal")
        }
}