package com.example.cookou.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookou.R;

import java.util.ArrayList;

public class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemsRecyclerAdapter.ViewHolder>{

    private ArrayList<String> itemList;
    private ArrayList<String> selectedItems;
    private Context context;

    public ItemsRecyclerAdapter(ArrayList<String> itemList, ArrayList<String> selectedItems, Context context) {
        this.itemList = itemList;
        this.selectedItems = selectedItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsRecyclerAdapter.ViewHolder holder, int position) {
        String item = itemList.get(position);
        holder.item.setText(item);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedItems.contains(item)){
                    selectedItems.remove(item);
                    holder.cardView.getBackground().setTint(context.getResources().getColor(R.color.secondary_variant1));
                }
                else {
                    selectedItems.add(item);
                    holder.cardView.getBackground().setTint(context.getResources().getColor(R.color.secondary_variant2));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView item;

        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardview_item);
            item=itemView.findViewById(R.id.textview_item);

        }
    }
}
