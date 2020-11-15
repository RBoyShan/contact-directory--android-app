package com.example.contact_directory.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contact_directory.R;
import com.example.contact_directory.activity.ContactDetailActivity;
import com.example.contact_directory.entity.Contact;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewAdapterHolder> {

    private final Context context;
    private ArrayList<Contact> contacts;

    public RecyclerViewAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contact_row, parent, false);

        return new RecyclerViewAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterHolder holder, int position) {
        holder.contactName.setText(this.contacts.get(position).getName());
        holder.contactPhone.setText(this.contacts.get(position).getPhone());

        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(this.context, ContactDetailActivity.class);
            intent.putParcelableArrayListExtra("Contacts", this.contacts);
            intent.putExtra("Position", position);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.contacts.size();
    }

    public class RecyclerViewAdapterHolder extends RecyclerView.ViewHolder {
        TextView contactName;
        TextView contactPhone;
        ConstraintLayout mainLayout;

        public RecyclerViewAdapterHolder(@NonNull View itemView) {
            super(itemView);

            this.contactName = itemView.findViewById(R.id.contactName);
            this.contactPhone = itemView.findViewById(R.id.contactPhone);
            this.mainLayout = itemView.findViewById(R.id.conractItemLayout);


        }
    }
}
