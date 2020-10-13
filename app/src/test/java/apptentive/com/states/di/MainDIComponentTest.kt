package apptentive.com.states.di

fun configureTestAppComponent(baseApi: String) = listOf(
    MockWebServerDITest,
    configureNetworkModuleForTest(baseApi),
    RepositoryDependency
)
