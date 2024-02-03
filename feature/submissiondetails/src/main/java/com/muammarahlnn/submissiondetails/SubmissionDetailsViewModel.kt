package com.muammarahlnn.submissiondetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.muammarahlnn.learnyscape.core.model.data.SubmissionType
import com.muammarahlnn.submissiondetails.navigation.SubmissionDetailsArgs
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionDetailsViewModel, 03/02/2024 15.29
 */
class SubmissionDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args = SubmissionDetailsArgs(savedStateHandle)

    val submissionType = SubmissionType.getSubmissionType(args.submissionTypeOrdinal)
}