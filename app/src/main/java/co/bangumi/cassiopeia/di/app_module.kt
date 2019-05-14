package co.bangumi.cassiopeia.di

import co.bangumi.cassiopeia.repository.DataRepository
import co.bangumi.cassiopeia.viewmodel.HomeViewModel
import co.bangumi.cassiopeia.viewmodel.LoginViewModel
import co.bangumi.common.network.ApiClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}

val serviceModule = module {
    single { ApiClient.getApiService() }
}

val repoModule = module {
    single { DataRepository(get()) }
}


val appModule = listOf(
    viewModelModule,
    serviceModule,
    repoModule
)
