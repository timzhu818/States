package apptentive.com.states.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import apptentive.com.states.models.StateInfo
import apptentive.com.states.repositories.StatesInfoRepository
import apptentive.com.states.util.LiveDataWrapper
import kotlinx.coroutines.*
import java.lang.Exception

class MainViewModel(
    mainDispatcher: CoroutineDispatcher,
    ioDispatcher: CoroutineDispatcher,
    private val repo: StatesInfoRepository
) : ViewModel() {

    private val _response = MutableLiveData<LiveDataWrapper<List<StateInfo>>>()
    val response: LiveData<LiveDataWrapper<List<StateInfo>>> = _response

    private val job = SupervisorJob()
    private val mUiScope = CoroutineScope(mainDispatcher + job)
    private val mIoScope = CoroutineScope(ioDispatcher + job)

    fun requestStatesInfo() {
        mUiScope.launch {
            _response.value = LiveDataWrapper.loading()
            try {
                val res = mIoScope.async {
                    return@async repo.getStatesInfo()
                }.await()
                try {
                    _response.value = LiveDataWrapper.success(res)
                } catch (e: Exception) {
                    _response.value = LiveDataWrapper.error(e)
                }
            } catch (e: Exception) {
                _response.value = LiveDataWrapper.error(e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
