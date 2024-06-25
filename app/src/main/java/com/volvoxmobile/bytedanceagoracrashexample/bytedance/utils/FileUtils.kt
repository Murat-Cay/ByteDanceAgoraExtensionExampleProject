package com.volvoxmobile.bytedanceagoracrashexample.bytedance.utils

import android.content.Context
import android.util.Log
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream

object FileUtils {
    val TAG = "FileUtils"

    fun getAssetsString(context: Context, path: String): String {
        val sb = StringBuilder()
        var isr: InputStreamReader? = null
        var br: BufferedReader? = null
        // 读取license文件内容
        try {
            isr = InputStreamReader(context.resources.assets.open(path))
            br = BufferedReader(isr)
            var line: String? = null
            while (br.readLine().also { line = it } != null) {
                sb.append(line).append("\n")
            }
        } catch (e: IOException) {
            Log.e(TAG, "getAssetsString error: $e")
        } finally {
            if (isr != null) {
                try {
                    isr.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (br != null) {
                try {
                    br.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return sb.toString()
    }

    fun copyAssets(context: Context, assetsPath: String, targetPath: String) {
        // 获取assets目录assetDir下一级所有文件以及文件夹
        val fileNames = context.resources.assets.list(assetsPath)
        // 如果是文件夹(目录),则继续递归遍历
        if (fileNames?.isNotEmpty() == true) {
            val targetFile = File(targetPath)
            if (!targetFile.exists() && !targetFile.mkdirs()) {
                return
            }
            for (fileName in fileNames) {
                copyAssets(
                    context,
                    "$assetsPath/$fileName",
                    "$targetPath/$fileName"
                )
            }
        } else {
            copyAssetsFile(context, assetsPath, targetPath)
        }
    }

    private fun copyAssetsFile(context: Context, assetsFile: String, targetPath: String) {
        val dest = File(targetPath)
        dest.parentFile?.mkdirs()
        var input: InputStream? = null
        var output: OutputStream? = null
        try {
            input = BufferedInputStream(context.assets.open(assetsFile))
            output = BufferedOutputStream(FileOutputStream(dest))
            val buffer = ByteArray(1024)
            var length = 0
            while (input.read(buffer).also { length = it } != -1) {
                output.write(buffer, 0, length)
            }
        } catch (e: Exception) {
            Log.e(TAG, "copyAssetsFile", e)
        } finally {
            output?.close()
            input?.close()
        }
    }
}