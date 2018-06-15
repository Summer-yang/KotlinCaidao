package com.summer.base.library.demo.caidao.share

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.GridLayoutManager
import com.summer.base.library.R
import com.summer.base.library.base.BaseActivity
import com.summer.caidao.extend.setClicker
import com.summer.caidao.toast.CaidaoToast
import kotlinx.android.synthetic.main.activity_share.*

class ActivityShare : BaseActivity(), View.OnClickListener, AdapterShare.OnShareClickListener {

    // 要分享的平台的icon
    private val icons = arrayOf(
            R.drawable.ic_we_chat,
            R.drawable.ic_sina,
            R.drawable.ic_google,
            R.drawable.ic_facebook,
            R.drawable.ic_content_copy)



    override fun getDataFromLastView(bundle: Bundle?) {

    }

    override fun getLayout(): Int {
        return R.layout.activity_share
    }

    override fun initView() {
        setClicker(clRootView)

        recyclerView.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = AdapterShare(icons, this)

        // 底部分享view执行动画
        val translateAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom)
        recyclerView.startAnimation(translateAnimation)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.clRootView -> {
                dismiss()
            }
        }
    }

    override fun onBackPressed() {
        dismiss()
    }

    private fun dismiss() {
        val translateAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_to_bottom)
        translateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                this@ActivityShare.finish()
            }

            override fun onAnimationStart(p0: Animation?) {

            }
        })
        recyclerView.startAnimation(translateAnimation)
    }

    override fun finish() {
        super.finish()

        // 当style中设置的退出动画不好用时,重写退出动画
        overridePendingTransition(0, 0)
    }

    override fun onShareItemClick(position: Int) {
        CaidaoToast.Builder(this).build().showShortSafe(position.toString())
    }
}
