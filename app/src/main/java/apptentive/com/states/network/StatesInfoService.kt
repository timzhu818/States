package apptentive.com.states.network

import apptentive.com.states.models.StateInfo
import retrofit2.http.GET

interface StatesInfoService {

    @GET("apptentive/states/master/states.json")
    suspend fun getStates(): List<StateInfo>

}
