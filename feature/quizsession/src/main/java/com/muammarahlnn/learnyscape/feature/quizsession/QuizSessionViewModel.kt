package com.muammarahlnn.learnyscape.feature.quizsession

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.muammarahlnn.learnyscape.feature.quizsession.navigation.QuizSessionArgs


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file QuizSessionViewModel, 28/08/2023 21.45 by Muammar Ahlan Abimanyu
 */
class QuizSessionViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val quizSessionArgs = QuizSessionArgs(savedStateHandle)

    val quizName = quizSessionArgs.quizName

    val quizDuration = quizSessionArgs.quizDuration
}