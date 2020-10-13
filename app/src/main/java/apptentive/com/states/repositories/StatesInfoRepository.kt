package apptentive.com.states.repositories

import apptentive.com.states.models.StateInfo
import apptentive.com.states.network.StatesInfoService
import org.koin.core.KoinComponent
import org.koin.core.inject

class StatesInfoRepository: KoinComponent {

    private val service: StatesInfoService by inject()

    suspend fun getStatesInfo(): List<StateInfo> {
        return service.getStates()
    }
}
