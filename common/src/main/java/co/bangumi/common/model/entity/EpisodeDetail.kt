package co.bangumi.common.model.entity

import android.text.TextUtils
import java.util.*

data class EpisodeDetail(
    var status: Int,
    var episode_no: Int,
    var update_time: Long,
    var name: String,
    var bgm_eps_id: Int,
    var bangumi_id: String,
    var airdate: String,
    var name_cn: String,
    var thumbnail: String,
    var delete_mark: Long,
    var create_time: Long,
    var duration: String,
    var id: String,
    var bangumi: Bangumi,

    var video_files: List<VideoFile>
) {
    val localName: String
        get() {
            return if (Locale.getDefault().displayLanguage == Locale.CHINESE.displayLanguage) {
                if (TextUtils.isEmpty(name_cn)) name else name_cn
            } else {
                if (TextUtils.isEmpty(name)) name_cn else name
            }
        }
}
