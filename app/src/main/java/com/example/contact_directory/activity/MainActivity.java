package com.example.contact_directory.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.contact_directory.R;
import com.example.contact_directory.entity.Contact;

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
        this.initGetStartedLoginButton();
        this.initGetStartedRegistrationButton();
    }

    private void initGetStartedLoginButton() {
        findViewById(R.id.startLoginButton).setOnClickListener(view -> {
            Intent contactDirectoryIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(contactDirectoryIntent);
        });
    }

    private void initGetStartedRegistrationButton() {
        findViewById(R.id.startRegistrationButton).setOnClickListener(view -> {
            Intent contactDirectoryIntent = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivity(contactDirectoryIntent);
        });
    }
}