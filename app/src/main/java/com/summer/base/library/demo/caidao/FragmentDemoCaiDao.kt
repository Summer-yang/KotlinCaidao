package com.summer.base.library.demo.caidao


import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.base.library.R
import com.summer.base.library.login.google.ActivityLoginGoogle
import com.summer.base.library.login.sina.ActivityLoginSian
import com.summer.caidao.check.click.CaiDaoDoubleClick
import com.summer.caidao.keyboard.dismiss.CaiDaoKeyboardDismiss
import com.summer.caidao.permission.CaiDaoPermission
import com.summer.caidao.toast.CaidaoToast
import kotlinx.android.synthetic.main.fragment_fragment_demo_cai_dao.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 *
 */
class FragmentDemoCaiDao : Fragment() {

    // 疯狂点击
    private val caiDaoDoubleClick = CaiDaoDoubleClick()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_demo_cai_dao, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CaiDaoKeyboardDismiss().useWith(this)

        doubleClick.setOnClickListener {
            if (caiDaoDoubleClick.isFastDoubleClick(R.id.doubleClick)) {
                CaidaoToast.Builder(context!!).build().showShortSafe("疯狂点击")
            } else {
                CaidaoToast.Builder(context!!).build().showShortSafe("正常点击")
            }
        }

        checkPermission.setOnClickListener {
            if (CaiDaoPermission().checkPermissionInFragment(this, 999, Manifest.permission.CAMERA)) {
                CaidaoToast.Builder(context!!).build().showShortSafe("已经有权限了")
            }
        }

        googleLogin.setOnClickListener {
            startActivity<ActivityLoginGoogle>()
        }

        sinaLogin.setOnClickListener {
            startActivity<ActivityLoginSian>()
        }
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

}
