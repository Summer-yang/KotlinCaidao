package com.summer.caidao.base64

import android.util.Base64

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/1
 *
 * Description:Base64编码解码工具类
 *
 */
class CaiDaoBase64 {

    /**
     * Base64编码
     *
     * @param input 要编码的字符串
     * @return Base64编码后的字符串
     */
    fun base64Encode(input: String): ByteArray {
        return base64Encode(input.toByteArray())
    }

    /**
     * Base64编码
     *
     * @param input 要编码的字节数组
     * @return Base64编码后的字符串
     */
    fun base64Encode(input: ByteArray): ByteArray {
        return Base64.encode(input, Base64.NO_WRAP)
    }

    /**
     * Base64编码
     *
     * @param input 要编码的字节数组
     * @return Base64编码后的字符串
     */
    fun base64Encode2String(input: ByteArray): String {
        return Base64.encodeToString(input, Base64.NO_WRAP)
    }

    /**
     * Base64解码
     *
     * @param input 要解码的字符串
     * @return Base64解码后的字符串
     */
    fun base64Decode(input: String): ByteArray {
        return Base64.decode(input, Base64.NO_WRAP)
    }

    /**
     * Base64解码
     *
     * @param input 要解码的字符串
     * @return Base64解码后的字符串
     */
    fun base64Decode(input: ByteArray): ByteArray {
        return Base64.decode(input, Base64.NO_WRAP)
    }

    /**
     * Base64URL安全编码
     *
     * 将Base64中的URL非法字符�?,/=转为其他字符, 见RFC3548
     *
     * @param input 要Base64URL安全编码的字符串
     * @return Base64URL安全编码后的字符串
     */
    fun base64UrlSafeEncode(input: String): ByteArray {
        return Base64.encode(input.toByteArray(), Base64.URL_SAFE)
    }
}