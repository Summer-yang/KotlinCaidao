package com.summer.caidao.entity

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/14
 *
 * Description:
 *
 */
data class CaiDaoEntityGetWeChatLoginToken(
        var access_token: String? = null,
        var expires_in: Int = 0,
        var refresh_token: String? = null,
        var openid: String? = null,
        var scope: String? = null,
        var unionid: String? = null
)