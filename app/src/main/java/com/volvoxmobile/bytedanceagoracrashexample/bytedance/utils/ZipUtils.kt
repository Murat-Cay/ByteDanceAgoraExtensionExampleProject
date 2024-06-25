package com.volvoxmobile.bytedanceagoracrashexample.bytedance.utils

import android.util.Log
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

object ZipUtils {
    private val TAG = "ZipUtils"
    private val INVALID_ZIP_ENTRY_NAME = arrayOf("../", "~/")

    fun unzip(zipFile: String, targetDir: String): Boolean {
        var unzipSuccess: Boolean
        try {
            unzipSuccess = true
            unzipWithException(zipFile, targetDir)
        } catch (e: Exception) {
            Log.w(TAG, "unzip: 解压失败")
            unzipSuccess = false
        }
        return unzipSuccess
    }

    @Throws(Exception::class)
    private fun unzipWithException(zipFile: String, targetDir: String) {
        val bufferSize = 4096
        val fileInputStream = FileInputStream(zipFile)
        var bufferedOutputStream: BufferedOutputStream
        val zipInputStream = ZipInputStream(BufferedInputStream(fileInputStream))
        var entry: ZipEntry?
        zipInputStream.use { zis ->
            while (zis.nextEntry.also { entry = it } != null) {
                val data = ByteArray(bufferSize)
                val strEntry = entry!!.name
                require(validEntry(strEntry)) {
                    "unsecurity zipfile!"
                }
                val entryFile = File(targetDir, strEntry)
                if (entry!!.isDirectory) {
                    if (!entryFile.exists()) {
                        entryFile.mkdirs()
                    }
                } else {
                    val entryDir = File(entryFile.parent!!)
                    if (!entryDir.exists()) {
                        entryDir.mkdirs()
                    }
                    bufferedOutputStream =
                        BufferedOutputStream(FileOutputStream(entryFile), bufferSize)
                    bufferedOutputStream.use { bos ->
                        var count: Int
                        while (zis.read(data, 0, bufferSize).also { count = it } != -1) {
                            bos.write(data, 0, count)
                        }
                        bos.flush()
                    }
                }
            }
        }
    }

    private fun validEntry(name: String): Boolean {
        var i = 0
        val l: Int = INVALID_ZIP_ENTRY_NAME.size
        while (i < l) {
            if (name.contains(INVALID_ZIP_ENTRY_NAME[i])) {
                return false
            }
            ++i
        }
        return true
    }
}