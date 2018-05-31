package com.summer.caidao.math

/**
 * Created by
 * User -> Summer
 * Date -> 2018/5/30
 *
 * Description: 数学计算类
 *
 */

// 默认除法运算精度
const val DEF_DIV_SCALE = 3

class CaiDaoMath {

    /**
     * 加法
     * @param double1 被加数
     * @param double2 加数
     * @return 和
     */
    fun add(double1: Double, double2: Double): Double {
        return double1.plus(double2)
    }

    /**
     * 减法
     * @param double1 被减数
     * @param double2 减数
     * @return 差
     */
    fun sub(double1: Double, double2: Double): Double {
        return double1.minus(double2)
    }

    /**
     * 乘法
     * @param double1 被乘数
     * @param double2 乘数
     * @return 积
     */
    fun mul(double1: Double, double2: Double): Double {
        return double1.times(double2)
    }

    /**
     * 除法
     * @param double1 被除数
     * @param double2 除数
     * @return 商
     */
    fun div(double1: Double, double2: Double): Double {
        return double1.div(double2)
    }


}