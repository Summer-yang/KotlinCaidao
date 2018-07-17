package com.summer.caidao.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Created by Summer on 2018/1/30.
 *
 *
 * 剪贴板工具类
 */
object CaiDaoClipboardUtil {

    /**
     * 复制文本到剪贴板
     *
     * @param text 文本
     */
    fun copyTextToClipboard(context: Context, text: CharSequence) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.primaryClip = ClipData.newPlainText("text", text)
    }

    /**
     * 获取剪贴板的文本
     *
     * @return 剪贴板的文本
     */
    fun getClipboardText(context: Context): CharSequence? {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = clipboard.primaryClip
        if (clip != null && clip.itemCount > 0) {
            return clip.getItemAt(0).coerceToText(context)
        }
        return null
    }

    /**
     * 复制uri到剪贴板
     *
     * @param uri uri
     */
    fun copyUri(context: Context, uri: Uri) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.primaryClip = ClipData.newUri(context.contentResolver, "uri", uri)
    }

    /**
     * 获取剪贴板的uri
     *
     * @return 剪贴板的uri
     */
    fun getUri(context: Context): Uri? {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = clipboard.primaryClip
        if (clip != null && clip.itemCount > 0) {
            return clip.getItemAt(0).uri
        }
        return null
    }

    /**
     * 复制意图到剪贴板
     *
     * @param intent 意图
     */
    fun copyIntent(context: Context, intent: Intent) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.primaryClip = ClipData.newIntent("intent", intent)
    }

    /**
     * 获取剪贴板的意图
     *
     * @return 剪贴板的意图
     */
    fun getIntent(context: Context): Intent? {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = clipboard.primaryClip
        if (clip != null && clip.itemCount > 0) {
            return clip.getItemAt(0).intent
        }
        return null
    }

}
