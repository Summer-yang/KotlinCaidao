package com.summer.base.library.demo.caidao


import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.summer.base.library.R
import com.summer.base.library.demo.caidao.dismiss.keyboard.ActivityDismissKeyboard
import com.summer.base.library.demo.caidao.login.ActivityLogin
import com.summer.base.library.demo.caidao.share.ActivityShare
import com.summer.caidao.check.click.CaiDaoDoubleClick
import com.summer.caidao.permission.CaiDaoPermission
import com.summer.caidao.toast.CaidaoToast
import kotlinx.android.synthetic.main.fragment_fragment_demo_cai_dao.*

/**
 * A simple [Fragment] subclass.
 *
 */
class FragmentDemoCaiDao : Fragment(), AdapterMenuList.OnItemClickListener {

    // 疯狂点击
    private val caiDaoDoubleClick = CaiDaoDoubleClick()

    private val items = arrayOf(
            "测试点击空白位置收键盘的工具类",
            "疯狂连击检测",
            "运行时权限请求",
            "Toast工具类",
            "登录",
            "分享")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_demo_cai_dao, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvMenuList.adapter = AdapterMenuList(items, this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        CaiDaoPermission().onPermissionResultInFragment(this, permissions, grantResults, object : CaiDaoPermission.OnPermissionResultListener {
            override fun onSuccess() {
                CaidaoToast.Builder(context!!).build().showShortSafe("请求成功")
            }

            override fun onShouldShow(shouldShowPermission: List<String>) {
                CaidaoToast.Builder(context!!)
                        .setBackgroundColor(Color.BLACK)
                        .setMessageColor(Color.WHITE)
                        .build().showShortSafe("应该给用户提示")
            }

            override fun onFailed() {
                CaidaoToast.Builder(context!!).build().showShortSafe("被拒绝了")
            }
        })
    }

    override fun itemClick(view: View, position: Int) {
        when (position) {
            0 -> {
                startActivity(Intent(context, ActivityDismissKeyboard::class.java))
            }
            1 -> {
                if (caiDaoDoubleClick.isFastDoubleClick()) {
                    CaidaoToast.Builder(view.context).build().showShortSafe("疯狂连击")
                } else {
                    CaidaoToast.Builder(view.context).build().showShortSafe("普通点击")
                }
            }
            2 -> {
                if (CaiDaoPermission().checkPermissionInFragment(this, 999, Manifest.permission.CAMERA)) {
                    CaidaoToast.Builder(context!!).build().showShortSafe("已经有权限了")
                }
            }
            3 -> {
                CaidaoToast.Builder(view.context)
                        .setBackgroundColor(Color.BLACK)
                        .setMessageColor(Color.WHITE)
                        .build().showShortSafe("CaidaoToast展示")
            }
            4 -> {
                startActivity(Intent(context, ActivityLogin::class.java))
            }
            5 -> {
                startActivity(Intent(context, ActivityShare::class.java))
            }
        }
    }
}
