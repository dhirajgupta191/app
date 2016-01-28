package com.example.laptop.status.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laptop.status.Bean.StatusBean;
import com.example.laptop.status.Bean.TestBean;
import com.example.laptop.status.R;
import com.example.laptop.status.values.RobotoTextView;

import java.util.ArrayList;

/**
 * Created by Laptop on 22-Dec-15.
 */
public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    ArrayList<StatusBean> arrayList;
    public StatusAdapter(Context context,ArrayList<StatusBean> arrayList)
    {
        layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflate_status,parent,false);
        MyViewHolder holder = new MyViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        StatusBean statusBean = arrayList.get(position);
        holder.timeTextView.setText(statusBean.getTime());
        holder.statusTextView.setText(statusBean.getStatus_text());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        RobotoTextView timeTextView,statusTextView;

        public MyViewHolder(View itemView) {
            super(itemView);

            timeTextView = (RobotoTextView) itemView.findViewById(R.id.status_time);
            statusTextView = (RobotoTextView) itemView.findViewById(R.id.status_text);

        }
    }



}
