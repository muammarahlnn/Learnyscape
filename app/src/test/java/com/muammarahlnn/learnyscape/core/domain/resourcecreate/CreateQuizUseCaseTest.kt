package com.muammarahlnn.learnyscape.core.domain.resourcecreate

import com.muammarahlnn.learnyscape.core.testing.repository.TestResourceCreateRepository
import com.muammarahlnn.learnyscape.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

/**
 * @Author Muammar Ahlan Abimanyu
 * @File CreateQuizUseCaseTest, 27/05/2024 01.04
 */
class CreateQuizUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val resourceCreateRepository = TestResourceCreateRepository()

    private val useCase = CreateQuizUseCase(resourceCreateRepository)

    @Test
    fun `createQuiz returns expected response`() = runTest {
        // given
        val createQuiz = useCase(
            classId = "",
            title = "",
            description = "",
            quizType = "",
            questions = listOf(),
            startDate = LocalDateTime.now(),
            endDate = LocalDateTime.now(),
            duration = 0,
        )

        // when
        resourceCreateRepository.quizCreated(createdQuizId = "quizId123")
        resourceCreateRepository.quizQuestionsAdded(testResponse)

        // then
        assertEquals(
            createQuiz.first(),
            testResponse
        )
    }
}

private const val testResponse = "response"