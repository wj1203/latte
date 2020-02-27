package com.leap.common_lib.joylearning.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * @author: leap
 * @time: 2020/2/14
 * @classname: GradualViewGroup
 * @description:  享学vip 自定义viewGroup—子view渐渐缩进
 */
class GradualViewGroup(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {


        val OFFSET = 100


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        // 1. 测量自己
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // 2. 测量子view
        for(i in 0..childCount){
            val child = getChildAt(i)
            // 获取child 的measureSpec  (parent的measureSpec + 自己的layoutParams => 自己的measureSpec)
            val childWidthSpec = getChildMeasureSpec(widthMeasureSpec,0,child.layoutParams.width)
            val childheightSpec = getChildMeasureSpec(heightMeasureSpec,0,child.layoutParams.height)
            // 测量child
            child.measure(childWidthSpec,childheightSpec)
        }

        // 3. 计算自己的宽高 (通过measureSpec => 宽高)
            // 计算宽度
        var width = 0
        when(MeasureSpec.getMode(widthMeasureSpec)){
            MeasureSpec.UNSPECIFIED ->{}
            MeasureSpec.EXACTLY -> {
                width = MeasureSpec.getSize(widthMeasureSpec)
            }
            MeasureSpec.AT_MOST ->{
                for(i in 0..childCount){
                    val child = getChildAt(i)
                    var offset = child.measuredWidth + i * OFFSET
                    width = Math.max(offset,width)
                }
            }
            else -> {}
        }
            // 计算高度
        var height = 0
        when(MeasureSpec.getMode(heightMeasureSpec)){
            MeasureSpec.UNSPECIFIED ->{}
            MeasureSpec.AT_MOST ->{
                for (i in 0..childCount){
                    val child = getChildAt(i)
                    height += child.measuredHeight
                }
            }
            MeasureSpec.EXACTLY ->{height = MeasureSpec.getSize(heightMeasureSpec)}
            else->{}
        }

        // 4. 保存自己的宽高
        setMeasuredDimension(width,height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var top = 0
        var bottom = 0
        var left = 0
        var right = 0
        //  确定每一个child的顶点
        for (i in 0..childCount){
            val child = getChildAt(i)
            bottom = top + child.measuredHeight
            left = OFFSET * i
            right = left + child.measuredHeight
            child.layout(left,top,right,bottom)
            top += child.measuredHeight
        }
    }


}