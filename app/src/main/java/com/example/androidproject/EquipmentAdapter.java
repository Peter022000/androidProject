package com.example.androidproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Item> items;

    public void setUid(int uid) {
        this.uid = uid;
    }

    private int uid;

    public void setUserMoney(int userMoney) {
        this.userMoney = userMoney;
    }

    private int userMoney;

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
        int uidToSend = this.uid;
        holder.item_name.setText(String.valueOf(items.get(position).getName()));
        holder.item_value.setText(String.valueOf(items.get(position).getValue()));
        holder.item_amount.setText(String.valueOf(items.get(position).getAmount()));
        holder.linearLayoutEquipmentItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EquipmentItemPreview.class);
                intent.putExtra("iid", items.get(holder.getAdapterPosition()).getId());
                intent.putExtra("name", items.get(holder.getAdapterPosition()).getName());
                intent.putExtra("description", items.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("value",  items.get(holder.getAdapterPosition()).getValue());
                intent.putExtra("weight",  items.get(holder.getAdapterPosition()).getWeight());
                intent.putExtra("amount",  items.get(holder.getAdapterPosition()).getAmount());
                intent.putExtra("uid", uidToSend);
                intent.putExtra("userMoney", userMoney);
//                Toast.makeText(context, ""+holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item_name, item_value, item_amount;
        LinearLayout linearLayoutEquipmentItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_value = itemView.findViewById(R.id.item_value);
            item_amount = itemView.findViewById(R.id.item_amount);
            linearLayoutEquipmentItem = itemView.findViewById(R.id.linearLayoutEquipmentItem);
        }
    }
}
