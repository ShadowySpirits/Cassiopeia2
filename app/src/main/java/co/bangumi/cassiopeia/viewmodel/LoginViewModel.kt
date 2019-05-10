package co.bangumi.cassiopeia.viewmodel

import androidx.lifecycle.MutableLiveData
import co.bangumi.cassiopeia.repository.DataRepository
import co.bangumi.framework.base.BaseViewModel
import co.bangumi.framework.network.MessageResponse
import co.bangumi.framework.util.helper.requestAsync
import kotlinx.coroutines.Deferred
import retrofit2.Response

class LoginViewModel(private val repo: DataRepository) : BaseViewModel() {
    val user = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    private val taskId: String = getUUID()

    suspend fun login(): Deferred<Response<MessageResponse>> {
        return requestAsync { repo.login(user.value!!, password.value!!) }.startSingleInstance(taskId)
    }
}