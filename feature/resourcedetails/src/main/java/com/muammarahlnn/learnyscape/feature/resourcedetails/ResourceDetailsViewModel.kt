package com.muammarahlnn.learnyscape.feature.resourcedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.muammarahlnn.learnyscape.feature.resourcedetails.navigation.ResourceDetailsArgs


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ResourceDetailsViewModel, 21/08/2023 21.10 by Muammar Ahlan Abimanyu
 */
class ResourceDetailsViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val resourceDetailsArgs = ResourceDetailsArgs(savedStateHandle)

    val resourceType = resourceDetailsArgs.resourceType
}