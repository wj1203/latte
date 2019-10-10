package com.leap.latte.recyclerview.scrollRecycler;

/**
 * Created by  wj
 * Created at  2019/10/9
 * TouchAdapter的接口
 */
public interface TouchAdapterInterface{
    // 拖动item的回调
    void onItemMove(int fromPosition, int toPosition);
    // 滑动item后删除的回调
    void onItemDismiss(int position);
}
