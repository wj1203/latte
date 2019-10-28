package com.leap.latte.filepicker.fragment;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leap.latte.R;
import com.leap.latte.filepicker.bean.FileEntity;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * @author: leap
 * @time: 2019/10/28
 * @classname: FilePickerAdapter
 * @description:
 */
public class FilePickerAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<FileEntity> dataList;

    public FilePickerAdapter(Context context, ArrayList<FileEntity> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_file_picker_recycler, viewGroup, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Holder holder = (Holder) viewHolder;
        String path = dataList.get(i).getPath();
        holder.textView.setText(dataList.get(i).getName()+"   "+ path);
//      holder.imageView.setImageURI(Uri.parse(path));

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivKind);
            textView = itemView.findViewById(R.id.tvName);
        }
    }
}
