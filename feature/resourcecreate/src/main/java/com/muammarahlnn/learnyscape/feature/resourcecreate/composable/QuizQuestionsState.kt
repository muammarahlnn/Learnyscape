package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.muammarahlnn.learnyscape.feature.resourcecreate.QuizType

/**
 * @Author Muammar Ahlan Abimanyu
 * @File QuizQuestionsScreen, 07/01/2024 04.32
 */
@Composable
internal fun rememberQuizQuestionQuestionsState(
    quizType: QuizType,
    multipleChoiceQuestions: List<MultipleChoiceQuestion>,
    photoAnswerQuestions: List<PhotoAnswerQuestion>,
): QuizQuestionsState {
    return remember(
        quizType,
        multipleChoiceQuestions,
        photoAnswerQuestions,
    ) {
        QuizQuestionsState(
            quizType,
            multipleChoiceQuestions,
            photoAnswerQuestions,
        )
    }
}

@Stable
internal class QuizQuestionsState(
    val quizType: QuizType,
    multipleChoiceQuestions: List<MultipleChoiceQuestion>,
    photoAnswerQuestions: List<PhotoAnswerQuestion>,
) {

    val multipleChoiceQuestions: MutableList<MultipleChoiceQuestion> = mutableStateListOf()

    val photoAnswerQuestions: MutableList<PhotoAnswerQuestion> = mutableStateListOf()

    val isCheckingUnfilled = mutableStateOf(false)

    init {
        this.multipleChoiceQuestions.addAll(multipleChoiceQuestions)
        this.photoAnswerQuestions.addAll(photoAnswerQuestions)
    }

    fun initializeQuestions() {
        when (quizType) {
            QuizType.MCQ -> if (multipleChoiceQuestions.isEmpty()) onAddQuestion()
            QuizType.PHOTO_ANSWER -> if (photoAnswerQuestions.isEmpty()) onAddQuestion()
            QuizType.NONE -> Unit
        }
    }

    fun onQuestionChange(index: Int, question: String) {
        when (quizType) {
            QuizType.MCQ -> multipleChoiceQuestions[index].question.value = question
            QuizType.PHOTO_ANSWER -> photoAnswerQuestions[index].question.value = question
            QuizType.NONE -> Unit
        }
    }

    fun onOptionChange(
        index: Int,
        option: MultipleChoiceOption,
        text: String,
    ) {
        multipleChoiceQuestions[index].options[option]?.value = text
    }

    fun onAddQuestion() {
        when (quizType) {
            QuizType.MCQ -> multipleChoiceQuestions.add(MultipleChoiceQuestion())
            QuizType.PHOTO_ANSWER -> photoAnswerQuestions.add(PhotoAnswerQuestion())
            QuizType.NONE -> Unit
        }
    }

    fun onDeleteQuestion(index: Int) {
        when (quizType) {
            QuizType.MCQ -> multipleChoiceQuestions.removeAt(index)
            QuizType.PHOTO_ANSWER -> photoAnswerQuestions.removeAt(index)
            QuizType.NONE -> Unit
        }
    }

    fun isUnfilledFieldExists(): Boolean {
        when (quizType) {
            QuizType.MCQ -> {
                multipleChoiceQuestions.forEach {
                    if (it.question.value.isEmpty()) return true
                }
                return false
            }
            QuizType.PHOTO_ANSWER -> {
                photoAnswerQuestions.forEach {
                    if (it.question.value.isEmpty()) return true
                }
                return false
            }
            QuizType.NONE -> return false
        }
    }
}