package co.bangumi.cassiopeia.viewmodel

import androidx.lifecycle.MutableLiveData
import co.bangumi.cassiopeia.repository.DataRepository
import co.bangumi.common.annotation.RECOMMENDATION
import co.bangumi.common.annotation.WATCHING
import co.bangumi.common.model.entity.Bangumi
import co.bangumi.framework.base.BaseViewModel
import co.bangumi.framework.util.extension.requestAsync
import kotlinx.coroutines.Deferred

class HomeViewModel(private val repo: DataRepository) : BaseViewModel() {
    private val getAnnounceTaskId: String = getUUID()
    private val getMyBangumiTaskId: String = getUUID()
    private val getOnAirTaskId: String = getUUID()

    val announceList = MutableLiveData<List<Bangumi>>()
    val watchingList = MutableLiveData<List<Bangumi>>()
    val onAirList = MutableLiveData<List<Bangumi>>()

    suspend fun getAnnounceBangumiAsync(): Deferred<Unit?> {
        return requestAsync(repo::getAnnounceBangumi) {
            it.getData().toHashSet()
                .sortedBy { it.sort_order }
                .groupBy { it.position }[RECOMMENDATION]?.let {
                announceList.postValue(it.map { it.bangumi })
            }
        }.startSingleInstance(getAnnounceTaskId)
    }

    suspend fun getMyBangumiAsync(): Deferred<Unit> {
        return requestAsync(repo::getMyBangumi) {
            watchingList.postValue(it.getData().filter {
                return@filter it.favorite_status == WATCHING
            }.sortedBy { -it.unwatched_count })
        }.startSingleInstance(getMyBangumiTaskId)
    }

    suspend fun getOnAirAsync(): Deferred<Unit> {
        return requestAsync(repo::getOnAir) {
            onAirList.postValue(it.getData())
        }.startSingleInstance(getOnAirTaskId)
    }
}
