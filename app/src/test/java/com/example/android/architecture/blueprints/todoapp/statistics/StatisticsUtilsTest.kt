package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class StatisticsUtilsTest {
    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {

        // Create an active tasks (the false makes this active)
        val tasks = listOf<Task>(
                Task("title", "desc", isCompleted = false)
        )
        // Call our function
        val result = getActiveAndCompletedStats(tasks)

        // Check the result
        assertThat(result.completedTasksPercent, `is`(0f))
        assertEquals(result.activeTasksPercent, 100f)
    }
}