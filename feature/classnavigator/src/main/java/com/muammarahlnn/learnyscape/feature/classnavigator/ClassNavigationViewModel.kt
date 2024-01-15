package com.muammarahlnn.learnyscape.feature.classnavigator

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.muammarahlnn.learnyscape.feature.classnavigator.navigation.ClassNavigatorArgs

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassNavigationViewModel, 14/01/2024 13.31
 */
class ClassNavigationViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val classNavigatorArgs = ClassNavigatorArgs(savedStateHandle)

    val classId = classNavigatorArgs.classId
}