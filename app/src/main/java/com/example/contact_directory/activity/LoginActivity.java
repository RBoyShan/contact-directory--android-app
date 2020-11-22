package com.example.contact_directory.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contact_directory.DB.DBHelper;
import com.example.contact_directory.R;
import com.example.contact_directory.entity.User;
import com.example.contact_directory.helpers.ContactValidatorHelper;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private TextView nameField;
    private TextView passwordField;

    private DBHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.initActivity();
    }

    private void initActivity() {
        this.initFields();
        this.initLoginButton();
        this.getUsersFromDB();
        this.initBackButton();
    }

    private void initFields() {
        this.nameField = this.findViewById(R.id.loginUsername);
        this.passwordField = this.findViewById(R.id.loginPassword);
    }

    private void initLoginButton() {
        this.findViewById(R.id.loginButton).setOnClickListener(view -> {
            if(this.validateData()) {
                String name = this.nameField.getText().toString();
                this.redirectToContactDirection(this.databaseHelper.getUserId(name));
            }
        });
    }

    private void initBackButton() {
        this.findViewById(R.id.loginBackButton).setOnClickListener(view -> { onBackPressed(); });
    }

    private ArrayList<User> getUsersFromDB() {
        this.databaseHelper = new DBHelper(this);

        try {
            return this.databaseHelper.getUsers();
        } catch (SQLException e) {
            Toast.makeText(this, R.string.database_error_message, Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        }
    }

    private boolean validateData() {
        String name = this.nameField.getText().toString();
        String password = this.passwordField.getText().toString();

        if (ContactValidatorHelper.isNullOrEmpty(name)) {
            Toast.makeText(getApplicationContext(), R.string.empty_name_message, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!this.databaseHelper.userExists(name)) {
            Toast.makeText(getApplicationContext(), R.string.user_not_exists_message, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (ContactValidatorHelper.isNullOrEmpty(password)) {
            Toast.makeText(getApplicationContext(), R.string.empty_password_message, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!this.databaseHelper.checkUserPassword(name, password)) {
            Toast.makeText(getApplicationContext(), R.string.password_confirm_error_message, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void redirectToContactDirection(int userID) {
        Intent intent = new Intent(LoginActivity.this, ContactDirectory.class);
        intent.putExtra("UserID", userID);
        startActivity(intent);
    }
}