package co.bangumi.framework.util

import android.os.Environment
import java.io.File

fun checkOrCreateFolder(dir: String): Boolean {
    val folder = Environment.getExternalStoragePublicDirectory(dir)
    return if (folder.exists() && folder.isDirectory) true else folder.mkdirs()
}

fun isFileExist(uri: String): Boolean {
    val file = Environment.getExternalStoragePublicDirectory(uri)
    return file.exists() && file.isFile
}

fun isFileExist(file: File): Boolean {
    return file.exists() && file.isFile
}

