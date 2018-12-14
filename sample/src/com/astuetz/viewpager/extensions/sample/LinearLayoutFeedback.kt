package com.astuetz.viewpager.extensions.sample

import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * Created by Javier on 23/03/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class LinearLayoutFeedback @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
    private val mForegroundDrawable: Drawable?

    init {
        val a = getContext().obtainStyledAttributes(intArrayOf(android.R.attr.selectableItemBackground))
        mForegroundDrawable = a.getDrawable(0)
        if (mForegroundDrawable != null) {
            mForegroundDrawable.callback = this
        }
        a.recycle()
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        mForegroundDrawable!!.state = drawableState
        invalidate()
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        mForegroundDrawable!!.draw(canvas)
    }

    override fun onSizeChanged(width: Int, height: Int, oldwidth: Int, oldheight: Int) {
        super.onSizeChanged(width, height, oldwidth, oldheight)
        mForegroundDrawable!!.setBounds(0, 0, width, height)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun drawableHotspotChanged(x: Float, y: Float) {
        super.drawableHotspotChanged(x, y)
        mForegroundDrawable?.setHotspot(x, y)
    }

    override fun verifyDrawable(who: Drawable): Boolean {
        return super.verifyDrawable(who) || who === mForegroundDrawable
    }

    override fun jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState()
        mForegroundDrawable!!.jumpToCurrentState()
    }
}