package apptentive.com.states.base

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import java.io.File

abstract class BaseUnitTest : KoinTest {

    private lateinit var mMockWebServer: MockWebServer

    @Before
    open fun setUp() {
        mMockWebServer = MockWebServer()
        mMockWebServer.start()
    }

    fun mockNetworkResponseWithFileContent(fileName: String, responseCode: Int) = mMockWebServer.enqueue(
        MockResponse().setResponseCode(responseCode).setBody(getJson(fileName))
    )

    fun getJson(fileName: String): String {
        val file = File(javaClass.classLoader!!.getResource(fileName).path)
        return String(file.readBytes())
    }

    fun getMockWebServerUrl() = mMockWebServer.url("/").toString()

    @After
    open fun tearDown() {
        mMockWebServer.shutdown()
        stopKoin()
    }
}
