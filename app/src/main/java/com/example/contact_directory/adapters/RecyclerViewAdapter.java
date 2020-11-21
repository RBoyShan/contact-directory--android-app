package com.example.contact_directory.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contact_directory.R;
import com.example.contact_directory.activity.ContactDetailActivity;
import com.example.contact_directory.activity.NewContactForm;
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

        holder.setCurrentPosition(position);

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

    public class RecyclerViewAdapterHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView contactName;
        TextView contactPhone;
        ConstraintLayout mainLayout;
        int currentPosition;

        public RecyclerViewAdapterHolder(@NonNull View itemView) {
            super(itemView);

            this.contactName = itemView.findViewById(R.id.contactName);
            this.contactPhone = itemView.findViewById(R.id.contactPhone);
            this.mainLayout = itemView.findViewById(R.id.conractItemLayout);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem contextMenuEdit = contextMenu.add(Menu.NONE, R.id.context_menu_edit,
                    Menu.NONE, R.string.menu_option_edit);
            MenuItem contextMenuDelete = contextMenu.add(Menu.NONE, R.id.context_menu_delete,
                    Menu.NONE, R.string.menu_option_delete);

            contextMenuEdit.setOnMenuItemClickListener(onEditMenu);
            contextMenuDelete.setOnMenuItemClickListener(onEditMenu);
        }

        public void setCurrentPosition(int position) {
            this.currentPosition = position;
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.context_menu_edit:
                        Intent intent = new Intent(context, NewContactForm.class);
                        intent.putParcelableArrayListExtra("Contacts", contacts);
                        intent.putExtra("Index", currentPosition);
                        context.startActivity(intent);
                        break;

                    case R.id.context_menu_delete:
                        contacts.remove(currentPosition);
                        notifyDataSetChanged();
                        break;
                }
                return true;
            }
        };
    }
}
