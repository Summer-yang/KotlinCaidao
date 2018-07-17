package com.summer.caidao.behavior

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.marginRight
import com.google.android.material.animation.AnimationUtils

/**
 * Created by
 * User -> Summer
 * Date -> 2018/7/16
 *
 * Description:
 *
 */
class CaiDaoBehaviorFAB<V : View>(context: Context, attributeSet: AttributeSet) : CoordinatorLayout.Behavior<V>(context, attributeSet) {
    private val enterAnimationDuration = 225L
    private val exitAnimationDuration = 175L

    private val stateScrolledRight = 1
    private val stateScrolledLeft = 2

    private var right = 0
    private var currentState = 2

    private var currentAnimator: ViewPropertyAnimator? = null

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
        right = child.marginRight + child.measuredWidth
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: V, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: V, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        if (currentState == stateScrolledLeft && dyConsumed > 0) {
            slideRight(child)
        } else if (currentState == stateScrolledRight && dyConsumed < 0) {
            slideLeft(child)
        }
    }

    private fun slideRight(child: V) {
        if (null != currentAnimator) {
            this.currentAnimator!!.cancel()
            child.clearAnimation()
        }

        this.currentState = 1
        this.animateChildTo(child, this.right, exitAnimationDuration, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR)
    }

    private fun slideLeft(child: V) {
        if (this.currentAnimator != null) {
            this.currentAnimator!!.cancel()
            child.clearAnimation()
        }

        this.currentState = 2
        this.animateChildTo(child, 0, enterAnimationDuration, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR)
    }

    private fun animateChildTo(child: V, targetX: Int, duration: Long, interpolator: TimeInterpolator) {
        this.currentAnimator = child.animate().translationX(targetX.toFloat()).setInterpolator(interpolator).setDuration(duration).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                this@CaiDaoBehaviorFAB.currentAnimator = null
            }
        })
    }
}