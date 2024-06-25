package com.volvoxmobile.bytedanceagoracrashexample.bytedance

import android.content.Context
import android.util.Log
import com.effectsar.labcv.effectsdk.RenderManager
import com.volvoxmobile.bytedanceagoracrashexample.bytedance.utils.FileUtils
import com.volvoxmobile.bytedanceagoracrashexample.Localizations
import io.agora.beautyapi.bytedance.ByteDanceBeautyAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

object ByteDanceBeautySDK {

    private const val TAG = "ByteDanceBeautySDK"

    private const val LICENSE_NAME = "volvox_20240326_20250325_com.fun2lite.app_4.5.2_1369.licbag"
    private var storagePath = ""
    private var assetsPath = ""
    private var licensePath = ""
    private var modelsPath = ""
    var beautyNodePath = ""
    var beauty4ItemsNodePath = ""
    var reSharpNodePath = ""
    var stickerPath = ""
    var stickerParamPath = ""
    var contrastPath = ""
    var huePath = ""
    var saturationPath = ""
    var acneRemovalPath = ""
    var reshapeLiteEyeSizePath = ""

    private val nodesLoaded = mutableListOf<String>()

    private var beautyAPI: ByteDanceBeautyAPI? = null

    val renderManager = RenderManager()

    val beautyConfig = BeautyConfig()

    fun initBeautySDK(context: Context): Boolean {
        storagePath = context.getExternalFilesDir("")?.absolutePath ?: return false
        assetsPath = "beauty_bytedance"

        // copy license
        licensePath = "$storagePath/beauty_bytedance/LicenseBag.bundle/$LICENSE_NAME"
        FileUtils.copyAssets(context, "$assetsPath/LicenseBag.bundle/$LICENSE_NAME", licensePath)
        if (!File(licensePath).exists()) {
            return false
        }

        // copy models
        modelsPath = "$storagePath/beauty_bytedance/ModelResource.bundle"
        FileUtils.copyAssets(context, "$assetsPath/ModelResource.bundle", modelsPath)

        // copy beauty node
        beautyNodePath =
            "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/beauty_Android_lite"
        FileUtils.copyAssets(
            context,
            "$assetsPath/ComposeMakeup.bundle/ComposeMakeup/beauty_Android_lite",
            beautyNodePath
        )

        // copy beauty node
        contrastPath =
            "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/palette/contrast"
        FileUtils.copyAssets(
            context,
            "$assetsPath/ComposeMakeup.bundle/ComposeMakeup/palette/contrast",
            contrastPath
        )

        huePath =
            "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/palette/light"
        FileUtils.copyAssets(
            context,
            "$assetsPath/ComposeMakeup.bundle/ComposeMakeup/palette/light",
            huePath
        )

        saturationPath =
            "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/palette/color"
        FileUtils.copyAssets(
            context,
            "$assetsPath/ComposeMakeup.bundle/ComposeMakeup/palette/color",
            saturationPath
        )

        acneRemovalPath =
            "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/hack/acne_removal_lite"
        FileUtils.copyAssets(
            context,
            "$assetsPath/ComposeMakeup.bundle/ComposeMakeup/hack/acne_removal_lite",
            acneRemovalPath
        )

        // copy beauty 4items node
        beauty4ItemsNodePath =
            "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/beauty_4Items"
        FileUtils.copyAssets(
            context,
            "$assetsPath/ComposeMakeup.bundle/ComposeMakeup/beauty_4Items",
            beauty4ItemsNodePath
        )

        // copy resharp node
        reSharpNodePath =
            "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/reshape_lite"
        FileUtils.copyAssets(
            context,
            "$assetsPath/ComposeMakeup.bundle/ComposeMakeup/reshape_lite",
            reSharpNodePath
        )

        reshapeLiteEyeSizePath =
            "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/reshape_lite_eye_size"
        FileUtils.copyAssets(
            context,
            "$assetsPath/ComposeMakeup.bundle/ComposeMakeup/reshape_lite_eye_size",
            reshapeLiteEyeSizePath
        )

        // copy stickers
        stickerPath = "$storagePath/beauty_bytedance/StickerResource.bundle/stickers"
        FileUtils.copyAssets(context, "$assetsPath/StickerResource.bundle/stickers", stickerPath)

        stickerParamPath = "$storagePath/beauty_bytedance/StickerResource.bundle/stickers/background_blur_in"
        FileUtils.copyAssets(context, "$assetsPath/StickerResource.bundle/stickers/background_blur_in", stickerParamPath)

        return true
    }

    // GL Thread
    fun initEffect(context: Context) {
        val ret = renderManager.init(
            context,
            modelsPath, licensePath, false, false, 0
        )
        if (!checkResult("RenderManager init ", ret)) {
            return
        }
        renderManager.useBuiltinSensor(true)
        renderManager.set3Buffer(false)

        renderManager.appendComposerNodes(
            nodesLoaded.toTypedArray()
        )

        renderManager.loadResourceWithTimeout(-1)
        //beautyConfig.resume()
    }

    fun unInitEffect() {
        beautyAPI = null
        nodesLoaded.clear()
        //beautyConfig.reset()
        renderManager.release()
    }

    private fun checkResult(msg: String, ret: Int): Boolean {
        if (ret != 0 && ret != -11 && ret != 1) {
            val log = "$msg error: $ret"
            Log.e(TAG, log)
            return false
        }
        return true
    }

    private fun runOnBeautyThread(run: () -> Unit) {
        beautyAPI?.runOnProcessThread(run) ?: run.invoke()
    }

    private fun loadNodeIfNotLoaded(node: String) {
        if (!nodesLoaded.contains(node)) {
            nodesLoaded.add(node)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    renderManager.appendComposerNodes(arrayOf(node))
                    renderManager.loadResourceWithTimeout(-1)
                } catch (e: Exception) {
                    Log.e(TAG, "Error loading node: $node", e)
                }
            }
        }
    }


    class BeautyConfig {

        var contrast = 0.5f // 0 65 değerini ben verdim
            set(value) {
                field = value
                runOnBeautyThread {
                    if (value > 0) {
                        loadNodeIfNotLoaded(contrastPath)
                    }
                    renderManager.updateComposerNodes(contrastPath, "Intensity_Contrast", value)
                }
            }

        var hue = 0.5f
            set(value) {
                field = value
                runOnBeautyThread {
                    if (value > 0) {
                        loadNodeIfNotLoaded(huePath)
                    }
                    renderManager.updateComposerNodes(huePath, "Intensity_Hue", value)
                }
            }

        var saturation = 0.5f
            set(value) {
                field = value
                runOnBeautyThread {
                    if (value > 0) {
                        loadNodeIfNotLoaded(saturationPath)
                    }
                    renderManager.updateComposerNodes(saturationPath, "Intensity_Saturation", value)
                }
            }

        var temperature = 0.5f
            set(value) {
                field = value
                runOnBeautyThread {
                    if (value > 0) {
                        loadNodeIfNotLoaded(saturationPath)
                    }
                    renderManager.updateComposerNodes(saturationPath, "Intensity_Temperature", value)
                }
            }

        var acneRemoval = 0.5f
            set(value) {
                field = value
                runOnBeautyThread {
                    if (value > 0) {
                        loadNodeIfNotLoaded(acneRemovalPath)
                    }
                    renderManager.updateComposerNodes(acneRemovalPath, "clean", value)
                }
            }

        var stickerParam = 0.5f
            set(value) {
                field = value
                runOnBeautyThread {
                    if (value > 0) {
                        loadNodeIfNotLoaded(stickerParamPath)
                    }
                    renderManager.updateComposerNodes(stickerParamPath, "Blur_intensity", value)
                }
            }

        var clearImageQuality = 0.5f
            set(value) {
                field = value
                runOnBeautyThread {
                    if (value > 0) {
                        loadNodeIfNotLoaded(beautyNodePath)
                    }
                    renderManager.updateComposerNodes(beautyNodePath, "clear", value)
                }
            }


        // 磨皮
        var smooth = 0.5f
            set(value) {
                field = value
                runOnBeautyThread {
                    if (value > 0) {
                        loadNodeIfNotLoaded(beautyNodePath)
                    }
                    renderManager.updateComposerNodes(beautyNodePath, "smooth", value)
                }
            }

        // 美白
        var whiten = 0.5f
            set(value) {
                field = value
                runOnBeautyThread {
                    if (value > 0) {
                        loadNodeIfNotLoaded(beautyNodePath)
                    }
                    renderManager.updateComposerNodes(beautyNodePath, "whiten", value)
                }
            }

        // 红润
        var redden = 0.5f
            set(value) {
                field = value
                runOnBeautyThread {
                    if (value > 0) {
                        loadNodeIfNotLoaded(beautyNodePath)
                    }
                    renderManager.updateComposerNodes(beautyNodePath, "sharp", value)
                }
            }

        var enlargeEye = 0.5f
            set(value) {
                field = value
                runOnBeautyThread {
                    if (value > 0) {
                        loadNodeIfNotLoaded(reshapeLiteEyeSizePath)
                    }
                    renderManager.updateComposerNodes(reshapeLiteEyeSizePath, "Internal_Deform_Eye", value)
                }
            }

        var shrinkCheekbone = 0.3f
            set(value) {
                field = value
                runOnBeautyThread {
                    if (value > 0) {
                        loadNodeIfNotLoaded(reSharpNodePath)
                    }
                    renderManager.updateComposerNodes(reSharpNodePath, "Internal_Deform_Overall", value)
                }
            }

        var shrinkJawbone = 0.5f
            set(value) {
                field = value
                runOnBeautyThread {
                    if (value > 0) {
                        loadNodeIfNotLoaded(reSharpNodePath)
                    }
                    renderManager.updateComposerNodes(reSharpNodePath, "Internal_Deform_Zoom_Jawbone", value)
                }
            }

        var whiteTeeth = 0.5f
            set(value) {
                field = value
                runOnBeautyThread {
                    if (value > 0) {
                        loadNodeIfNotLoaded(beauty4ItemsNodePath)
                    }
                    renderManager.updateComposerNodes(beauty4ItemsNodePath, "BEF_BEAUTY_WHITEN_TEETH", value)
                }
            }

        var narrowNose = 0.5f
            set(value) {
                field = value
                runOnBeautyThread {
                    if (value > 0) {
                        loadNodeIfNotLoaded(reSharpNodePath)
                    }
                    renderManager.updateComposerNodes(reSharpNodePath, "Internal_Deform_Nose", value)
                }
            }

        var mouthSize = 0.5f
            set(value) {
                field = value
                runOnBeautyThread {
                    if (value > 0) {
                        loadNodeIfNotLoaded(reSharpNodePath)
                    }
                    renderManager.updateComposerNodes(reSharpNodePath, "Internal_Deform_ZoomMouth", value)
                }
            }


        var makeUpLenses: MakeUpItem? = null
            set(value) {
                if (field == value) return

                val oldMakeUp = field
                field = value

                val newStyle = value?.style
                val oldStyle = oldMakeUp?.style

                if (oldStyle != newStyle) {
                    oldMakeUp?.let {
                        runOnBeautyThread {
                            val oldNodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/pupil/$oldStyle"
                            renderManager.removeComposerNodes(arrayOf(oldNodePath))
                        }
                    }

                    value?.let {
                        val newNodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/pupil/$newStyle"
                        FileUtils.copyAssets(
                            it.context,
                            "$assetsPath/ComposeMakeup.bundle/ComposeMakeup/pupil/$newStyle",
                            newNodePath
                        )
                        runOnBeautyThread {
                            renderManager.appendComposerNodes(arrayOf(newNodePath))
                            renderManager.loadResourceWithTimeout(-1)
                        }
                    }
                }

                value?.let {
                    val nodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/pupil/$newStyle"
                    runOnBeautyThread {
                        renderManager.updateComposerNodes(nodePath, "Internal_Makeup_Pupil", it.identity)
                        renderManager.updateComposerNodes(nodePath, "Internal_Makeup_Pupil", it.identity)
                    }
                }
            }

        var makeUpLip: MakeUpItem? = null
            set(value) {
                if (field == value) return

                val oldMakeUp = field
                field = value

                val newStyle = value?.style
                val oldStyle = oldMakeUp?.style

                if (oldStyle != newStyle) {
                    oldMakeUp?.let {
                        runOnBeautyThread {
                            val oldNodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/lip/lite/$oldStyle"
                            renderManager.removeComposerNodes(arrayOf(oldNodePath))
                        }
                    }

                    value?.let {
                        val newNodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/lip/lite/$newStyle"
                        FileUtils.copyAssets(
                            it.context,
                            "$assetsPath/ComposeMakeup.bundle/ComposeMakeup/lip/lite/$newStyle",
                            newNodePath
                        )
                        runOnBeautyThread {
                            renderManager.appendComposerNodes(arrayOf(newNodePath))
                            renderManager.loadResourceWithTimeout(-1)
                        }

                    }
                }

                value?.let {
                    val nodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/lip/lite/$newStyle"
                    runOnBeautyThread {
                        renderManager.updateComposerNodes(nodePath, "Internal_Makeup_Lips", it.identity)
                        renderManager.updateComposerNodes(nodePath, "Internal_Makeup_Lips", it.identity)
                    }
                }
            }

        var makeUpHairColor: MakeUpItem? = null
            set(value) {
                if (field == value) return

                val oldMakeUp = field
                field = value

                val newStyle = value?.style
                val oldStyle = oldMakeUp?.style

                if (oldStyle != newStyle) {
                    oldMakeUp?.let {
                        runOnBeautyThread {
                            val oldNodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/hair/$oldStyle"
                            renderManager.removeComposerNodes(arrayOf(oldNodePath))
                        }
                    }

                    value?.let {
                        val newNodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/hair/$newStyle"
                        FileUtils.copyAssets(
                            it.context,
                            "$assetsPath/ComposeMakeup.bundle/ComposeMakeup/hair/$newStyle",
                            newNodePath
                        )
                        runOnBeautyThread {
                            renderManager.appendComposerNodes(arrayOf(newNodePath))
                            renderManager.loadResourceWithTimeout(-1)
                        }

                    }
                }

                value?.let {
                    val nodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/hair/$newStyle"
                    runOnBeautyThread {
                        renderManager.updateComposerNodes(nodePath, "", it.identity)
                        renderManager.updateComposerNodes(nodePath, "", it.identity)
                    }
                }
            }

        var makeUpBlush: MakeUpItem? = null
            set(value) {
                if (field == value) return

                val oldMakeUp = field
                field = value

                val newStyle = value?.style
                val oldStyle = oldMakeUp?.style

                if (oldStyle != newStyle) {
                    oldMakeUp?.let {
                        runOnBeautyThread {
                            val oldNodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/blush/lite/$oldStyle"
                            renderManager.removeComposerNodes(arrayOf(oldNodePath))
                        }
                    }

                    value?.let {
                        val newNodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/blush/lite/$newStyle"
                        FileUtils.copyAssets(
                            it.context,
                            "$assetsPath/ComposeMakeup.bundle/ComposeMakeup/blush/lite/$newStyle",
                            newNodePath
                        )
                        runOnBeautyThread {
                            renderManager.appendComposerNodes(arrayOf(newNodePath))
                            renderManager.loadResourceWithTimeout(-1)
                        }

                    }
                }

                value?.let {
                    val nodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/blush/lite/$newStyle"
                    runOnBeautyThread {
                        renderManager.updateComposerNodes(nodePath, "Internal_Makeup_Blusher", it.identity)
                        renderManager.updateComposerNodes(nodePath, "Internal_Makeup_Blusher", it.identity)
                    }
                }
            }

        var makeUpContour: MakeUpItem? = null
            set(value) {
                if (field == value) return

                val oldMakeUp = field
                field = value

                val newStyle = value?.style
                val oldStyle = oldMakeUp?.style

                if (oldStyle != newStyle) {
                    oldMakeUp?.let {
                        runOnBeautyThread {
                            val oldNodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/facial/$oldStyle"
                            renderManager.removeComposerNodes(arrayOf(oldNodePath))
                        }
                    }

                    value?.let {
                        val newNodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/facial/$newStyle"
                        FileUtils.copyAssets(
                            it.context,
                            "$assetsPath/ComposeMakeup.bundle/ComposeMakeup/facial/$newStyle",
                            newNodePath
                        )
                        runOnBeautyThread {
                            renderManager.appendComposerNodes(arrayOf(newNodePath))
                            renderManager.loadResourceWithTimeout(-1)
                        }

                    }
                }

                value?.let {
                    val nodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/facial/$newStyle"
                    runOnBeautyThread {
                        renderManager.updateComposerNodes(nodePath, "Internal_Makeup_Facial", it.identity)
                        renderManager.updateComposerNodes(nodePath, "Internal_Makeup_Facial", it.identity)
                    }
                }
            }

        var makeUpEyeBrow: MakeUpItem? = null
            set(value) {
                if (field == value) return

                val oldMakeUp = field
                field = value

                val newStyle = value?.style
                val oldStyle = oldMakeUp?.style

                if (oldStyle != newStyle) {
                    oldMakeUp?.let {
                        runOnBeautyThread {
                            val oldNodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/eyebrow/$oldStyle"
                            renderManager.removeComposerNodes(arrayOf(oldNodePath))
                        }
                    }

                    value?.let {
                        val newNodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/eyebrow/$newStyle"
                        FileUtils.copyAssets(
                            it.context,
                            "$assetsPath/ComposeMakeup.bundle/ComposeMakeup/eyebrow/$newStyle",
                            newNodePath
                        )
                        runOnBeautyThread {
                            renderManager.appendComposerNodes(arrayOf(newNodePath))
                            renderManager.loadResourceWithTimeout(-1)
                        }

                    }
                }

                value?.let {
                    val nodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/eyebrow/$newStyle"
                    runOnBeautyThread {
                        renderManager.updateComposerNodes(nodePath, "Internal_Makeup_Brow", it.identity)
                        renderManager.updateComposerNodes(nodePath, "Internal_Makeup_Brow", it.identity)
                    }
                }
            }

        var makeUpEyeShadow: MakeUpItem? = null
            set(value) {
                if (field == value) return

                val oldMakeUp = field
                field = value

                val newStyle = value?.style
                val oldStyle = oldMakeUp?.style

                if (oldStyle != newStyle) {
                    oldMakeUp?.let {
                        runOnBeautyThread {
                            val oldNodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/eyeshadow/$oldStyle"
                            renderManager.removeComposerNodes(arrayOf(oldNodePath))
                        }
                    }

                    value?.let {
                        val newNodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/eyeshadow/$newStyle"
                        FileUtils.copyAssets(
                            it.context,
                            "$assetsPath/ComposeMakeup.bundle/ComposeMakeup/eyeshadow/$newStyle",
                            newNodePath
                        )
                        runOnBeautyThread {
                            renderManager.appendComposerNodes(arrayOf(newNodePath))
                            renderManager.loadResourceWithTimeout(-1)
                        }

                    }
                }

                value?.let {
                    val nodePath = "$storagePath/beauty_bytedance/ComposeMakeup.bundle/ComposeMakeup/eyeshadow/$newStyle"
                    runOnBeautyThread {
                        renderManager.updateComposerNodes(nodePath, "Internal_Makeup_Eye", it.identity)
                        renderManager.updateComposerNodes(nodePath, "Internal_Makeup_Eye", it.identity)
                    }
                }
            }


        var sticker: String? = null
            set(value) {
                if (field == value) {
                    return
                }
                field = value
                runOnBeautyThread {
                    if (value != null) {
                        renderManager.setSticker("$stickerPath/$value")
                    } else {
                        renderManager.setSticker(null)
                    }
                }
            }

        internal fun pagePosition0to0Remover() {
         /*   removeNodes(
                arrayOf(reshapeLiteEyeSizePath,reSharpNodePath, beauty4ItemsNodePath)
            )*/
            shrinkJawbone = 0f
            shrinkCheekbone = 0f
            narrowNose = 0f
            mouthSize = 0f
            whiteTeeth = 0f
            enlargeEye = 0f
            makeUpEyeShadow = null
        }

        internal fun disabledNarrow() {
            narrowNose = 0f
        }

        internal fun pagePosition0to1Remover() {
            //removeNodes(arrayOf(reSharpNodePath))
            shrinkJawbone = 0f
            shrinkCheekbone = 0f
        }

        internal fun pagePosition0to2Remover() {
            //removeNodes(arrayOf(reshapeLiteEyeSizePath))
            enlargeEye = 0f
            makeUpEyeShadow = null
        }

        internal fun pagePosition0to3Remover() {
            //removeNodes(arrayOf(reSharpNodePath, beauty4ItemsNodePath))
            mouthSize = 0f
            whiteTeeth = 0f
        }

        internal fun pagePosition0to4Remover() {
           //removeNodes(arrayOf(beautyNodePath, contrastPath, reshapeLiteEyeSizePath, saturationPath, huePath))
            clearImageQuality = 0f
            smooth = 0f
            whiten = 0f
            redden = 0f
            contrast = 0f
            hue = 0f
            saturation = 0f
            temperature = 0f
        }

        internal fun pagePosition0to5Remover() {
            acneRemoval = 0f
            //removeNodes(arrayOf(acneRemovalPath))
            makeUpLip = null
            makeUpLenses = null
            makeUpHairColor = null
            makeUpBlush = null
            makeUpEyeBrow = null
            makeUpContour = null
        }

        internal fun pagePosition0to6Remover() {
            makeUpHairColor = null
        }

        internal fun pagePosition0to7Remover() {
            makeUpBlush = null
        }

        internal fun pagePosition0to8Remover() {
            makeUpContour = null
        }

        internal fun pagePosition0to9Remover() {
            makeUpEyeBrow = null
        }

        internal fun eyeShadowDisabled() {
            makeUpEyeShadow = null
        }

        internal fun pagePosition0to10Remover() {
            //removeNodes(arrayOf(stickerParamPath))
            stickerParam = 0f
            sticker = null
        }

        internal fun removeAllEffect() {
            makeUpLip = null
            makeUpLenses = null
            makeUpHairColor = null
            makeUpBlush = null
            makeUpEyeBrow = null
            makeUpContour = null
            makeUpEyeShadow = null
            sticker = null
            stickerParam = 0f
            whiteTeeth = 0f
            clearImageQuality = 0f
            smooth = 0f
            whiten = 0f
            redden = 0f
            contrast = 0f
            enlargeEye = 0f
            saturation = 0f
            hue = 0f
            acneRemoval = 0f
            shrinkCheekbone = 0f
            shrinkCheekbone = 0f
            narrowNose = 0f
            mouthSize = 0f

            /*            removeNodes(arrayOf(stickerParamPath, beautyNodePath, contrastPath, reshapeLiteEyeSizePath, saturationPath, huePath, acneRemovalPath, beauty4ItemsNodePath,
                            reSharpNodePath))*/
        }

        internal fun effectsReady() {
            clearImageQuality = 0.3f
            smooth = 0.3f
            redden = 0.25f
            enlargeEye = 0.35f
            whiteTeeth = 0.8f
            shrinkCheekbone = 0.3f
        }

        fun removeNodes(paths: Array<String>) {
            if (paths.isEmpty()) return

            CoroutineScope(Dispatchers.IO).launch {
                paths.forEach { path ->
                    withContext(Dispatchers.Default) {
                        renderManager.removeComposerNodes(arrayOf(path))
                        nodesLoaded.remove(path)
                        Log.e(TAG, "This Effect has been deleted: $path")
                    }
                }
            }
        }

        fun getPathForUnique(unique: String) {
             when(unique) {
                  "make_up_item_lens" -> {
                    makeUpLenses = null
                }
                 "make_up_item_Lip" -> {
                     makeUpLip = null
                 }
                 "make_up_item_HairColor" -> {
                     makeUpHairColor = null
                 }
                 "make_up_item_blush" -> {
                     makeUpBlush = null
                 }
                 "make_up_item_contour" -> {
                     makeUpContour = null
                 }
                 "make_up_item_eyebrow" -> {
                     makeUpEyeBrow = null
                 }
                 "ready_effects" -> {
                     removeAllEffect()
                 }
            }
        }

        fun getPathForProperty(propertyName: String, context: Context) {
            when (propertyName) {
                Localizations.get(context = context, "make_up_item_acne_removal") -> acneRemoval = 0f
                Localizations.get(context = context, "image_quality_item_clear") -> clearImageQuality = 0f
                Localizations.get(context = context, "image_quality_item_smooth") -> smooth = 0f
                Localizations.get(context = context, "image_quality_item_whiteen") -> whiten = 0f
                Localizations.get(context = context, "image_quality_item_sharp") -> redden = 0f
                Localizations.get(context = context, "beauty_item_eye_size") -> enlargeEye = 0f

                Localizations.get(context = context, "beauty_item_deform") -> shrinkCheekbone = 0f
                Localizations.get(context = context, "beauty_item_jawbone") -> shrinkJawbone = 0f
                Localizations.get(context = context, "beauty_item_mouth_whiten_teeth") -> whiteTeeth = 0f
                Localizations.get(context = context, "beauty_item_nose_deform") -> narrowNose = 0f
                Localizations.get(context = context, "beauty_item_mouth_zoom") -> mouthSize = 0f

                Localizations.get(context = context, "image_quality_item_contrast") -> contrast = 0f
                Localizations.get(context = context, "image_quality_item_tone") -> hue = 0f
                Localizations.get(context = context, "image_quality_item_saturation") -> saturation = 0f
                Localizations.get(context = context, "image_quality_item_temperature") -> temperature = 0f

                Localizations.get(context = context, "background_blur_item_default") -> { pagePosition0to10Remover() }
                Localizations.get(context = context, "background_blur_item_enhanced") -> { pagePosition0to10Remover() }
                Localizations.get(context = context, "background_blur_item_adjustable") -> { pagePosition0to10Remover() }
            }
        }
    }


    data class MakeUpItem(
        val context: Context,
        val style: String,
        var identity: Float
    )
}