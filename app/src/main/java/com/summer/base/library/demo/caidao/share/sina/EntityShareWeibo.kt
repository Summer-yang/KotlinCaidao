package com.summer.base.library.demo.caidao.share.sina

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/6
 *
 * Description:
 *
 */
data class EntityShareWeibo(
        var shareMessage: String? = null,
        var shareImagePath: String? = null,
        var shareMultiImagePaths: ArrayList<String>? = null,
        var shareVideo: String? = null,
        var shareWebPagePath: String? = null
)