package com.summer.caidao.math

import java.math.BigDecimal


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
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后 DEF_DIV_SCALE 位，以后的数字四舍五入
     * @param double1 被除数
     * @param double2 除数
     * @return 两个参数的商
     */
    fun div(double1: Double, double2: Double): Double {
        return div(double1, double2, DEF_DIV_SCALE)
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     * @param double1 被除数
     * @param double2 除数
     * @param scale 保留小数的位数
     * @return 商
     */
    fun div(double1: Double, double2: Double, scale: Int): Double {
        if (0 < scale) {
            throw Exception("The scale must be a positive integer or zero")
        }
        val bigDecimal1 = BigDecimal(double1)
        val bigDecimal2 = BigDecimal(double2)
        return bigDecimal1.divide(bigDecimal2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }


}