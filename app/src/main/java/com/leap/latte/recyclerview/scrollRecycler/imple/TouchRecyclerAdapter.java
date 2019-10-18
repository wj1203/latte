package com.leap.latte.recyclerview.scrollRecycler.imple;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leap.latte.R;
import com.leap.latte.recyclerview.scrollRecycler.TouchAdapterInterface;
import com.leap.latte.recyclerview.scrollRecycler.TouchViewHolderInterface;

import java.util.ArrayList;

/**
 * @author: leap
 * @time: 2019/10/15
 * @classname: TouchRecyclerAdapter
 * @description:
 */
public class TouchRecyclerAdapter extends RecyclerView.Adapter implements TouchAdapterInterface {

    private Context context;
    private ArrayList<String> dataList;

    public TouchRecyclerAdapter(Context context, ArrayList<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_scroll_recycler,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Holder holder = (Holder) viewHolder;
        holder.tvDetail.setText("第"+dataList.get(i)+"个");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

//    拖动回调
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        String prev = dataList.remove(fromPosition);
        dataList.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    // 滑动回调
    @Override
    public void onItemDismiss(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    class Holder extends RecyclerView.ViewHolder implements TouchViewHolderInterface{
        TextView tvDetail;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tvDetail = itemView.findViewById(R.id.tvDetail);
        }
        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }
        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
