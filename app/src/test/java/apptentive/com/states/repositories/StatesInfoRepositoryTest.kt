package apptentive.com.states.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import apptentive.com.states.base.BaseUnitTest
import apptentive.com.states.di.configureTestAppComponent
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import java.net.HttpURLConnection

class StatesInfoRepositoryTest : BaseUnitTest() {

    private lateinit var mRepo: StatesInfoRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun start() {
        super.setUp()
        startKoin { modules(configureTestAppComponent(getMockWebServerUrl())) }
    }

    @Test
    fun test_states_info_repo_data_retrieved() = runBlocking {

        mockNetworkResponseWithFileContent(
            "states_info_list.json",
            HttpURLConnection.HTTP_OK
        )

        mRepo = StatesInfoRepository()
        val responseData = mRepo.getStatesInfo()
        assertNotNull(responseData)
        assertEquals(responseData.size, 50)
        assertEquals(responseData[0].name, "Alabama")
    }
}
