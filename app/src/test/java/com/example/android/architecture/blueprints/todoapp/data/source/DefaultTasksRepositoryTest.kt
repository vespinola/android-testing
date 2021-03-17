package com.example.android.architecture.blueprints.todoapp.data.source

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
class DefaultTasksRepositoryTest {
    private val task1 = Task("Title1", "Description1")
    private val task2 = Task("Title2", "Description2")
    private val task3 = Task("Title3", "Description3")
    private val remoteTasks = listOf(task1, task2).sortedBy { it.id }
    private val localTasks = listOf(task3).sortedBy { it.id }
    private val newTasks = listOf(task3).sortedBy { it.id }

    private lateinit var tasksRemoteDataSource: FakeDataSource
    private lateinit var tasksLocalDataSource: FakeDataSource

    // Class under test
    private lateinit var tasksRepository: DefaultTasksRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository() {
        tasksRemoteDataSource = FakeDataSource(remoteTasks.toMutableList())
        tasksLocalDataSource = FakeDataSource(localTasks.toMutableList())

        //  Dispatchers.Unconfined should be replaced with Dispatchers.Main
        //  this requires understanding more about coroutines + testing
        //  so we will keep this as Unconfined for now.
        tasksRepository = DefaultTasksRepository(
                tasksRemoteDataSource,
                tasksLocalDataSource,
                Dispatchers.Main
        )
    }

    @Test
    fun getTasks_requestAllTaskFromRemoteDataSource() = mainCoroutineRule.runBlockingTest {
        val tasks = tasksRepository.getTasks(true) as Result.Success
        assertThat(tasks.data, IsEqual(remoteTasks))
    }
}