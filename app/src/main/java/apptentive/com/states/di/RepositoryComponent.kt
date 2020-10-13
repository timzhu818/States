package apptentive.com.states.di

import apptentive.com.states.repositories.StatesInfoRepository
import org.koin.dsl.module

val RepositoryDependency = module {
    factory {
        StatesInfoRepository()
    }
}
