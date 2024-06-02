package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.datastore.model.UserEntity
import com.muammarahlnn.learnyscape.core.network.model.response.EnrolledClassInfoResponse
import com.muammarahlnn.learnyscape.core.testing.datasource.TestHomeNetworkDataSource
import com.muammarahlnn.learnyscape.core.testing.datasource.TestLearnyscapePreferencesDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @Author Muammar Ahlan Abimanyu
 * @File HomeRepositoryTest, 31/05/2024 19.18
 */
class HomeRepositoryTest {

    private val learnyscapePreferencesDataSource = TestLearnyscapePreferencesDataSource()

    private val homeNetworkDataSource = TestHomeNetworkDataSource()

    @Test
    fun homeRepository_update_logged_in_user_when_user_login() = runTest {
        learnyscapePreferencesDataSource.saveUser(sampleUserEntity)

        assertEquals(
            expected = sampleUserEntity,
            actual = learnyscapePreferencesDataSource.getUser().first(),
        )
    }

    @Test
    fun homeRepository_fetch_enrolled_classes_from_network() = runTest {
        homeNetworkDataSource.sendEnrolledClasses(sampleEnrolledClasses)

        assertEquals(
            expected = sampleEnrolledClasses,
            actual = homeNetworkDataSource.getEnrolledClasses().first(),
        )
    }

    private val sampleUserEntity = UserEntity(
        id = "id",
        username = "test",
        fullName = "Lorem Ipsum Dolor Sit Amet",
        role = "LECTURER,"
    )

    private val sampleEnrolledClasses = listOf(
        EnrolledClassInfoResponse(
            id = "1",
            className = "Android Development",
            lecturerNames = listOf(
                "Lorem Ipsum Dolor Sit Amet",
            )
        )
    )
}