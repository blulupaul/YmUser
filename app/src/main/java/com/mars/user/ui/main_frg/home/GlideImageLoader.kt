package com.mars.user.ui.main_frg.home

import android.content.Context
import android.widget.ImageView
import com.mars.user.utils.glideLoad

import com.youth.banner.loader.ImageLoader

/**
 * 作者：xcf   后期维护：gxdd by Administrator on 2017/1/12.
 */

class GlideImageLoader : ImageLoader() {

    override fun displayImage(context: Context?, path: Any, imageView: ImageView?) {
        try {
            /**
             * 注意：
             * 1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             * 2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             * 传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             * 切记不要胡乱强转！
             */
            if (imageView == null || context == null) return

            glideLoad(context, path, imageView)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun createImageView(context: Context): ImageView {
        return ImageView(context)
    }
}