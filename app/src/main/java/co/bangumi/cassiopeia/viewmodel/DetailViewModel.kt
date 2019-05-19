package co.bangumi.cassiopeia.viewmodel

import co.bangumi.cassiopeia.repository.DataRepository
import co.bangumi.common.model.entity.BangumiDetail
import co.bangumi.common.network.FavoriteChangeRequest
import co.bangumi.framework.base.BaseViewModel
import co.bangumi.framework.network.MessageResponse
import co.bangumi.framework.util.helper.requestAsync
import kotlinx.coroutines.Deferred

class DetailViewModel(private val repo: DataRepository) : BaseViewModel() {
    private val taskId = getUUID()

    suspend fun getBangumiDetailAsync(id: String): Deferred<BangumiDetail> {
        return requestAsync { repo.getBangumiDetail(id) }
    }

    suspend fun uploadFavoriteStatusAsync(id: String, request: FavoriteChangeRequest): Deferred<MessageResponse> {
        return requestAsync { repo.uploadFavoriteStatus(id, request) }.startSingleInstance(taskId)
    }
}