package com.summer.caidao.dialog

import android.app.Dialog
import android.content.Context
import com.summer.caidao.R

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/14
 *
 * Description:
 *
 */
object CaiDaoDialog {

    private var dialog: Dialog? = null

    fun show(context: Context) {
        cancel()
        dialog = Dialog(context, R.style.LoadingDialog)
        dialog?.setContentView(R.layout.loading_dialog)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.show()
    }

    fun cancel() {
        dialog?.dismiss()
    }

}