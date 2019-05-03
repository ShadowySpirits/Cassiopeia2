package co.bangumi.cassiopeia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.bangumi.cassiopeia.repository.DataRepository
import co.bangumi.framework.network.MessageResponse
import retrofit2.Response

class LoginViewModel(private val repo: DataRepository) : ViewModel() {
    val user = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    suspend fun login(): Response<MessageResponse> {
        return repo.login(user.value!!, password.value!!)
    }

}