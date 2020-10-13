package apptentive.com.states.di

fun generateTestAppComponent(baseApi: String) = listOf(
    configureNetworkForInstrumentTest(baseApi),
    MockWebServerInstrumentTest,
    RepositoryDependency
)
