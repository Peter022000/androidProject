package com.example.androidproject;

import android.content.Context;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Item> items;

    EquipmentAdapter(Context context, ArrayList<Item> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.equipment_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.item_name.setText(String.valueOf(items.get(position).getName()));
        holder.item_description.setText(String.valueOf(items.get(position).getDescription()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item_name, item_description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_description = itemView.findViewById(R.id.item_description);
        }
    }
}
