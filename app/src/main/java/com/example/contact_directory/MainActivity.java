package com.example.contact_directory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.contacts = new ArrayList<>();

        this.initActivity();
    }

    private void initActivity() {
        this.initGetStartedButton();
    }

    private void initGetStartedButton() {
        findViewById(R.id.buttonStart).setOnClickListener(view -> {
            Intent contactDirectoryIntent = new Intent(MainActivity.this, ContactDirectory.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("Contacts", this.contacts);
            contactDirectoryIntent.putExtras(bundle);
            startActivity(contactDirectoryIntent);
        });
    }
}