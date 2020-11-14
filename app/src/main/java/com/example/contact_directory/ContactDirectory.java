package com.example.contact_directory;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import java.util.ArrayList;
import java.util.HashSet;

public class ContactDirectory extends AppCompatActivity {

    private ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_directory);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.initActivity();
    }

    private void initActivity () {
        this.initCreateContactFab();
        this.getContacts();
    }

    private void initCreateContactFab() {
        findViewById(R.id.fabCreateContact).setOnClickListener(view -> {
            Intent createContactIntent = new Intent(ContactDirectory.this, NewContactForm.class);
            createContactIntent.putParcelableArrayListExtra("Contacts", this.contacts);
            startActivity(createContactIntent);
        });
    }

    private void getContacts() {
        this.contacts = getIntent().getParcelableArrayListExtra("Contacts");
    }
}