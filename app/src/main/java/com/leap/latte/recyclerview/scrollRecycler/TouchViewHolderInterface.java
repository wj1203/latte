package com.leap.latte.recyclerview.scrollRecycler;

/**
 * @author: leap
 * @time: 2019/10/15
 * @classname: TouchViewHolderInterface
 * @description:
 */
public interface TouchViewHolderInterface {
    //item选中的时候的回调
    void onItemSelected();
    //item放开的时候的回调
    void onItemClear();
}
