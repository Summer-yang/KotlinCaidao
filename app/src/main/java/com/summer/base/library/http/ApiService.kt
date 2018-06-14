package com.summer.base.library.http

import com.summer.caidao.entity.CaiDaoEntityGetWeChatLoginToken
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/14
 *
 * Description:
 *
 */
interface ApiService {

    @GET("https://api.weixin.qq.com/sns/oauth2/access_token?&grant_type=authorization_code")
    fun getYahooForStarList(@Query("appid") appid: String,
                            @Query("secret") secret: String,
                            @Query("code") code: String): Observable<CaiDaoEntityGetWeChatLoginToken>

}