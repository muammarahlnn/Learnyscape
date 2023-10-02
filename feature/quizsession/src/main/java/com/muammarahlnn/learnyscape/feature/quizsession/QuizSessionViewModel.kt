package com.muammarahlnn.learnyscape.feature.quizsession

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.muammarahlnn.learnyscape.core.model.ui.QuizType
import com.muammarahlnn.learnyscape.feature.quizsession.navigation.QuizSessionArgs


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file QuizSessionViewModel, 28/08/2023 21.45 by Muammar Ahlan Abimanyu
 */
class QuizSessionViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val quizSessionArgs = QuizSessionArgs(savedStateHandle)

    val quizType = getQuizTypeArg()

    val quizName = quizSessionArgs.quizName

    val quizDuration = quizSessionArgs.quizDuration

    val questions = generateDummyQuestions()

    val selectedOptionLetters = List(questions.size) {
        OptionLetter.UNSELECTED
    }.toMutableStateList()

    private fun getQuizTypeArg() = when (quizSessionArgs.quizTypeOrdinal) {
        QuizType.MULTIPLE_CHOICE_QUESTIONS.ordinal -> QuizType.MULTIPLE_CHOICE_QUESTIONS
        QuizType.PHOTO_ANSWER.ordinal -> QuizType.PHOTO_ANSWER
        else -> throw IllegalStateException("The given QuizType ordinal not matched any QuizType ordinals")
    }

    private fun generateDummyQuestions(): List<MultipleChoiceQuestion> =
        List(10) { index ->
            MultipleChoiceQuestion(
                id = index,
                question = "Lorem ipsum dolor sit amet. Est rerum fugit sed quia rerum qui nihil asperiores aut mollitia numquam. Eos quae fugiat qui illo accusamus eum beatae odio. Ab galisum reiciendis nam maxime explicabo cum atque corrupti et minima voluptatum eum tempora enim non perspiciatis rerum ab cumque ipsam.",
                options = listOf(
                    Option(OptionLetter.A, "Lorem Ipsum Dolor Sit Amet"),
                    Option(OptionLetter.B, "Lorem ipsum dolor sit amet. Est rerum fugit sed quia rerum qui nihil asperiores aut mollitia numquam"),
                    Option(OptionLetter.C, "Lorem Ipsum Dolor Sit Amet"),
                    Option(OptionLetter.D, "Lorem ipsum dolor sit amet. Est rerum fugit sed quia rerum qui nihil asperiores aut mollitia numquam"),
                    Option(OptionLetter.E, "Lorem Ipsum Dolor Sit Amet"),
                )
            )
        }
}