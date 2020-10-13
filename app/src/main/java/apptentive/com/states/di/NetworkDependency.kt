package apptentive.com.states.di

import apptentive.com.states.constants.BASE_URL
import apptentive.com.states.constants.TIME_OUT
import apptentive.com.states.network.StatesInfoService
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val NetworkDependency = module {

    single {
        val client = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL).build()
    }
    factory { get<Retrofit>().create(StatesInfoService::class.java) }
}
