package com.example.contact_directory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.SubMenu;

import com.example.contact_directory.R;
import com.example.contact_directory.adapters.RecyclerViewAdapter;
import com.example.contact_directory.entity.Contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactDirectory extends AppCompatActivity {

    private ArrayList<Contact> contacts;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_directory);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.contacts = new ArrayList<>();

        this.initActivity();
    }

    private void initActivity () {
        this.getContacts();
        this.initRecyclerView();
        this.initCreateContactFab();
    }

    private void initCreateContactFab() {
        findViewById(R.id.fabCreateContact).setOnClickListener(view -> {
            Intent createContactIntent = new Intent(ContactDirectory.this, NewContactForm.class);
            createContactIntent.putParcelableArrayListExtra("Contacts", this.contacts);
            startActivity(createContactIntent);
        });
    }

    private void initRecyclerView() {
        this.adapter = new RecyclerViewAdapter(this, this.contacts);

        this.recyclerView = this.findViewById(R.id.contactsRecyclerView);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getContacts() {
        this.contacts = getIntent().getParcelableArrayListExtra("Contacts");
    }
}