package com.example.contact_directory.activity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.SubMenu;
import android.widget.Toast;

import com.example.contact_directory.DB.DBHelper;
import com.example.contact_directory.R;
import com.example.contact_directory.adapters.RecyclerViewAdapter;
import com.example.contact_directory.entity.Contact;
import com.example.contact_directory.entity.User;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactDirectory extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    private DBHelper databaseHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_directory);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.initActivity();
    }

    private void initActivity () {
        this.getUserId();
        this.initRecyclerView();
        this.initCreateContactFab();
    }

    private void initCreateContactFab() {
        findViewById(R.id.fabCreateContact).setOnClickListener(view -> {
            Intent intent = new Intent(ContactDirectory.this, NewContactForm.class);
            intent.putExtra("UserID", this.userId);
            startActivity(intent);
        });
    }

    private void initRecyclerView() {
        this.adapter = new RecyclerViewAdapter(this, this.getContacts());

        this.recyclerView = this.findViewById(R.id.contactsRecyclerView);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getUserId() {
        this.userId = getIntent().getIntExtra("UserID", 0);
    }

    private ArrayList<Contact> getContacts() {
        this.databaseHelper = new DBHelper(this);

        try {
            return this.databaseHelper.getContacts(this.userId);
        } catch (SQLException e) {
            Toast.makeText(this, R.string.database_error_message, Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        }
    }
}