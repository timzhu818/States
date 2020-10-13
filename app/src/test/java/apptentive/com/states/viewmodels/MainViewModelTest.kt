package apptentive.com.states.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import apptentive.com.states.base.BaseUnitTest
import apptentive.com.states.di.configureTestAppComponent
import apptentive.com.states.models.StateInfo
import apptentive.com.states.repositories.StatesInfoRepository
import apptentive.com.states.util.LiveDataWrapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin

class MainViewModelTest : BaseUnitTest() {

    @get:Rule
    var instantExecuteRule = InstantTaskExecutorRule()

    lateinit var mViewModel: MainViewModel
    private val mDispatcher = Dispatchers.Unconfined

    @MockK
    lateinit var mRepo: StatesInfoRepository

    @Before
    fun start() {
        super.setUp()
        MockKAnnotations.init(this)
        startKoin { modules(configureTestAppComponent(getMockWebServerUrl())) }
    }

    @Test
    fun test_main_view_model_data_correct() {
        mViewModel = MainViewModel(mDispatcher, mDispatcher, mRepo)

        val statesData = getJson("states_info_list.json")
        val statesInfoData = Gson().fromJson<List<StateInfo>>(
            statesData,
            object : TypeToken<List<StateInfo>>() {}.type
        )
        coEvery { mRepo.getStatesInfo() } returns statesInfoData
        mViewModel.response.observeForever {  }
        mViewModel.requestStatesInfo()
        assert(mViewModel.response.value != null)
        assert(mViewModel.response.value?.responseStatus == LiveDataWrapper.RESPONSESTATUS.SUCCESS)
        assertEquals(
            (mViewModel.response.value?.response as List<StateInfo>)[0].name,
            "Alabama"
        )
    }
}
