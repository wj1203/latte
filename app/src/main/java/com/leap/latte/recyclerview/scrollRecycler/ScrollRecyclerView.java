package com.leap.latte.recyclerview.scrollRecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by  wj
 * Created at  2019/10/9
 * 自定义可滑动item的recyclerView
 */



/**  实现思路
 * 扩展ItemTouchHelper.Callback类，重写getMovementFlags()，onMove()，onSwiped()等方法
 * 建立RecyclerView配套的Adapter类的回调接口，Adapter类实现该接口，用于进行item删除和交换
 * 建立ViewHolder的回调接口，ViewHolder类实现该接口，用于改变item选中和放开时候的背景改变
 * 实例化ItemTouchHelper，绑定RecyclerView
 * */
public class ScrollRecyclerView extends RecyclerView {


    public ScrollRecyclerView(@NonNull Context context) {
        super(context);
    }
    public ScrollRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }



}
