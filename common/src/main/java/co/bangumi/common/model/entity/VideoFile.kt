package co.bangumi.common.model.entity

data class VideoFile(
    var torrentId: String,
    var status: Int,
    var url: String,
    var file_name: String,
    var resolutionW: Int,
    var downloadUrl: Any,
    var episodeId: String,
    var filePath: String,
    var resolutionH: Int,
    var bangumiId: String,
    var duration: Int,
    var label: Any,
    var id: String
)
