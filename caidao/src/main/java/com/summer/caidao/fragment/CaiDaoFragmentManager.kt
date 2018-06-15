package com.summer.caidao.fragment

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/15
 *
 * Description:
 * 单例模式
 * Fragment管理类
 *
 */
class CaiDaoFragmentManager private constructor() {

    companion object {
        fun get(): CaiDaoFragmentManager {
            return Inner.caiDaoFragmentManager
        }
    }

    private object Inner {
        val caiDaoFragmentManager = CaiDaoFragmentManager()
    }



}