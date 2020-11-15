package com.example.contact_directory.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contact_directory.R;
import com.example.contact_directory.entity.Contact;

import java.util.ArrayList;

public class ContactDetailActivity extends AppCompatActivity {

    private TextView name;
    private TextView phone;
    private TextView email;

    private ArrayList<Contact> contacts;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        this.initActivity();
    }

    private void initActivity() {
        this.getData();
        this.initViews();
        this.setData();
        this.initBackButton();
        this.initEditButton();
    }

    private void initViews() {
        this.name = this.findViewById(R.id.contactDetailName);
        this.phone = this.findViewById(R.id.contactDetailPhone);
        this.email = this.findViewById(R.id.contactDetailEmail);
    }

    private void getData() {
        Intent intent = getIntent();
        if(intent.hasExtra("Contacts") && intent.hasExtra("Position")) {
            this.contacts = intent.getParcelableArrayListExtra("Contacts");
            this.currentPosition = intent.getIntExtra("Position", -1);
        } else {
            Toast.makeText(this, "No contact", Toast.LENGTH_LONG).show();
        }
    }

    private void setData() {
        this.name.setText(this.contacts.get(this.currentPosition).getName());
        this.phone.setText(this.contacts.get(this.currentPosition).getPhone());
        this.email.setText(this.contacts.get(this.currentPosition).getEmail());
    }

    private void initBackButton() {
        this.findViewById(R.id.contactDetailBackButton).setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void initEditButton() {
        this.findViewById(R.id.contactDetailEditButton).setOnClickListener(view -> {
            Intent intent = new Intent(ContactDetailActivity.this, NewContactForm.class);
            intent.putParcelableArrayListExtra("Contacts", this.contacts);
            intent.putExtra("Index", this.currentPosition);
            this.startActivity(intent);
        });
    }
}