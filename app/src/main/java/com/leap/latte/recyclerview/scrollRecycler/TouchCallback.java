package com.leap.latte.recyclerview.scrollRecycler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by  wj
 * Created at  2019/10/9
 *   自定义Touch回调
 */
public class TouchCallback extends ItemTouchHelper.Callback{

    /**用于设置是否处理拖拽事件和滑动事件，以及拖拽和滑动操作的方向，*/
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // drag 表示拖动，swipe表示滑动
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**如果我们设置了相关的dragFlags ，
     * 那么当我们长按item的时候就会进入拖拽并在拖拽过程中不断回调onMove()方法,
     * 我们就在这个方法里获取当前拖拽的item和已经被拖拽到所处位置的item的ViewHolder。*/
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    /**如果我们设置了相关的swipeFlags，
     * 那么当我们滑动item的时候就会调用onSwipe()方法，
     * 一般的话在使用LinearLayoutManager的时候，在这个方法里可以删除item，来实现滑动删除！*/
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    /**是否开启长按拖拽 （默认开启）*/
    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }

    /**是否开启长按滑动（默认开启）*/
    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }

    /**这个方法在选中Item的时候（拖拽或者滑动开始的时候）调用，
     * 通常这个方法里我们可以改变选中item的背景颜色等，高亮表示选中来提高用户体验。*/
    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        // actionStated的可选值
        //ACTION_STATE_IDLE：闲置状态
        //ACTION_STATE_SWIPE：滑动状态
        //ACTION_STATE_DRAG：拖拽状态
    }

    /**这个方法在当手指松开的时候（拖拽或滑动完成的时候）调用，这时候我们可以将item恢复为原来的状态。*/
    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
    }
}