package com.summer.base.library

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.summer.base.library.demo.caidao.FragmentDemoCaiDao
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_tool_bar.*

class ActivityMain : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val fragmentNameDemoCaiDao = "FragmentDemoCaiDao"
    private var lastShowFragmentName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(mToolbar)

        val toggle = ActionBarDrawerToggle(
                this,
                drawer_layout,
                findViewById(R.id.mToolbar),
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        // 初始化第一个Fragment
        nav_view.setCheckedItem(R.id.nav_caidao)
        setFragment(fragmentNameDemoCaiDao)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_caidao -> {
                setFragment(FragmentDemoCaiDao::class.java.simpleName)
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setFragment(fragmentName: String?) {
        if (fragmentName == lastShowFragmentName) return

        val transaction = supportFragmentManager.beginTransaction()

        // 如果有一个已经显示的Fragment则隐藏
        if (null != lastShowFragmentName) {
            transaction.hide(supportFragmentManager.findFragmentByTag(lastShowFragmentName))
        }

        // 先查看是不是已经有这个Fragment了
        val fragment = supportFragmentManager.findFragmentByTag(fragmentName)
        // 如果有了则显示
        if (null == fragment) {
            when (fragmentName) {
                fragmentNameDemoCaiDao -> {
                    transaction.add(R.id.mFrameLayout, FragmentDemoCaiDao(), fragmentNameDemoCaiDao)
                }
            }
        } else {
            // 显示当前这个Fragment
            transaction.show(fragment)
        }
        transaction.commit()

        lastShowFragmentName = fragmentName
    }
}
