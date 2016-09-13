package com.augylab.ankur.foreignexchanger;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ankur on 9/13/16.
 */
public class CustomHomeAdapter extends RecyclerView.Adapter<CustomHomeAdapter.ViewHolder> {


    private ArrayList<AddRecord> listArray = null;
    Context c;
    private AddRecord recordDetail;
    private DatabaseHelper dbHelper;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView codeTv, quantityTv, investedTv, currentTv;

        public ViewHolder(View v) {
            super(v);
            codeTv = (TextView) v.findViewById(R.id.codeTv);
            quantityTv = (TextView) v.findViewById(R.id.qtyTv);
            investedTv = (TextView) v.findViewById(R.id.investedTv);
            currentTv = (TextView) v.findViewById(R.id.currentTv);
        }
    }

    public CustomHomeAdapter(Context ctx, ArrayList<AddRecord> listArray) {
        this.c = ctx;
        this.listArray = listArray;
        this.dbHelper = DatabaseHelper.getInstance(ctx);
    }



    @Override
    public CustomHomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_home_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;    }

    @Override
    public void onBindViewHolder(CustomHomeAdapter.ViewHolder holder, int position) {
        recordDetail = listArray.get(position);
        holder.codeTv.setText(recordDetail.getCode());
        holder.quantityTv.setText(recordDetail.getQty() + "");
        holder.investedTv.setText(recordDetail.getPaidValue() + "");

    }

    @Override
    public int getItemCount() {
        return listArray.size();
    }



}
