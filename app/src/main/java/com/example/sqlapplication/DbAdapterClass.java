package com.example.sqlapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DbAdapterClass extends RecyclerView.Adapter<DbAdapterClass.DbVirewHolderClass> {
    private ArrayList<DbModelClass> objectDbModelClassArrayList;

    public DbAdapterClass(ArrayList<DbModelClass> objectDbModelClassArrayList) {
        this.objectDbModelClassArrayList = objectDbModelClassArrayList;
    }

    @NonNull
    @Override
    public DbVirewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View Singleitem = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        DbVirewHolderClass objectDbViewHolderClass = new DbVirewHolderClass(Singleitem);
        return objectDbViewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull DbVirewHolderClass holder, int position) {
        holder.Name.setText(objectDbModelClassArrayList.get(position).getName());
        holder.Address.setText(objectDbModelClassArrayList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return objectDbModelClassArrayList.size();
    }

    class DbVirewHolderClass extends RecyclerView.ViewHolder {
        TextView Name, Address;

        public DbVirewHolderClass(@NonNull View Singleitem){
            super(Singleitem);
            Name = Singleitem.findViewById(R.id.si_name);
            Address = Singleitem.findViewById(R.id.si_address);
        }
    }
}
