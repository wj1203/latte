package com.leap.latte.recyclerview.scrollRecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by  wj
 * Created at  2019/10/9
 */
public class TouchAdapter extends RecyclerView.Adapter implements TouchAdapterInterface {

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemDismiss(int position) {

    }


    private Context context;
    private ArrayList dataList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(context)
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
