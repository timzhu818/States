package apptentive.com.states.ui

import android.os.SystemClock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import apptentive.com.states.R
import apptentive.com.states.activities.MainActivity
import apptentive.com.states.base.BaseUITest
import apptentive.com.states.di.generateTestAppComponent
import apptentive.com.states.util.recyclerItemAtPosition
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest : BaseUITest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val alabamaDetail = "\n   abbreviation :  AL \n   capital :  Montgomery " +
            "\n   establish date :  1819-12-14T12:00:00-05:00 \n   land area(square km) : 131171" +
            "\n   largest city :  Birmingham \n   name :  Alabama \n   numbers of reps : 7" +
            "\n   population : 4874747\n   total area(square km) : 135767\n   water area(square km) : 4597\n"
    private val hawaiiDetail = "\n   abbreviation :  HI \n   capital :  Honolulu " +
            "\n   establish date :  1959-08-21T12:00:00-05:00 \n   land area(square km) : 16635" +
            "\n   largest city :  Honolulu \n   name :  Hawaii \n   numbers of reps : 2" +
            "\n   population : 1427538\n   total area(square km) : 28313\n   water area(square km) : 11678\n"


    @Before
    fun start() {
        super.setUp()
        loadKoinModules(generateTestAppComponent(getMockWebServerUrl()).toMutableList())
    }

    @Test
    fun test_recyclerview_performance() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        mockNetworkResponseWithFileContent("states_info_list.json", HttpURLConnection.HTTP_OK)
        SystemClock.sleep(2000)

        onView(withId(R.id.recyclerView)).check(
            matches(
                recyclerItemAtPosition(
                    0, hasDescendant(
                        withText("Alabama")
                    )
                )
            )
        )
        onView(withId(R.id.recyclerView)).perform(
            actionOnItemAtPosition<StateAdapter.StateHolder>(
                0,
                click()
            )
        )
        onView(withId(android.R.id.message)).check(matches(withText(alabamaDetail)))
        onView(withId(android.R.id.button1)).perform(click())

        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.scrollToPosition<StateAdapter.StateHolder>(49)
        )
        onView(withId(R.id.recyclerView)).check(
            matches(
                recyclerItemAtPosition(
                    49, hasDescendant(
                        withText("Hawaii")
                    )
                )
            )
        )
        onView(withId(R.id.recyclerView)).perform(
            actionOnItemAtPosition<StateAdapter.StateHolder>(
                49,
                click()
            )
        )
        onView(withId(android.R.id.message)).check(matches(withText(hawaiiDetail)))
        onView(withId(android.R.id.button1)).perform(click())
    }

}
