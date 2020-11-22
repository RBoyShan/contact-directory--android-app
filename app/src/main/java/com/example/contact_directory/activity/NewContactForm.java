package com.example.contact_directory.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contact_directory.DB.DBHelper;
import com.example.contact_directory.helpers.ContactValidatorHelper;
import com.example.contact_directory.R;
import com.example.contact_directory.helpers.UniquenessContactHelper;
import com.example.contact_directory.entity.Contact;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class NewContactForm extends AppCompatActivity {
    private View baseView;

    private TextView nameField;
    private TextView phoneField;
    private TextView emailField;

    private Contact contact;
    private int contactId = -1;
    private boolean isEdit;

    private DBHelper databaseHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact_form3);

        this.contact = new Contact();

        this.isEdit = false;
        this.baseView = this.findViewById(R.id.contactFormView);

        this.initActivity();
    }

    private void initActivity () {
        if(this.getDataIndex()) {
            this.contact = this.getContactById(this.contactId);
            this.setValueData(this.contact);
        }

        this.initFormFields();
        this.initBackButton();
        this.initSubmitButton();
    }

    private void initBackButton() {
        this.findViewById(R.id.backToDirectoryButton).setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void initSubmitButton() {
        this.findViewById(R.id.createContactButton).setOnClickListener(view -> {
            this.setContactDataFromForm();

            if(!contact.isNotEmptyObject()) {
                return;
            }

            if(this.validateForm(contact)) {
                if(this.isEdit)  {
                    this.editContact(this.contact);
                } else {
                    this.addContact(this.contact);
                }
                this.redirectToContactList();
            }
        });
    }

    private boolean getDataIndex() {
        Intent intent = getIntent();

        this.userId = intent.getIntExtra("UserID", -1);
        this.contactId = intent.getIntExtra("ContactID", -1);

        if(this.contactId >= 0)
            this.isEdit = true;

        return this.isEdit;
    }

    private void initFormFields() {
        this.nameField = this.findViewById(R.id.contactFormAbonentName);
        this.phoneField = this.findViewById(R.id.contactFormAbonentPhone);
        this.emailField = this.findViewById(R.id.contactFormAbonentEmailAddress);
    }

    private void setValueData(Contact contact) {
        TextView name = this.findViewById(R.id.contactFormAbonentName);
        name.setText(contact.getName());

        TextView phone = this.findViewById(R.id.contactFormAbonentPhone);
        phone.setText(contact.getPhone());

        TextView email = this.findViewById(R.id.contactFormAbonentEmailAddress);
        email.setText(contact.getEmail());
    }

    private void setContactDataFromForm() {
        String name = this.nameField.getText().toString();
        String phone = this.phoneField.getText().toString();
        String email = this.emailField.getText().toString();

        this.contact.setName(name);
        this.contact.setPhone(phone);
        this.contact.setEmail(email);
    }

    private boolean validateForm(Contact newContact) {
        if (ContactValidatorHelper.isNullOrEmpty(newContact.getName())) {
            Snackbar.make(this.baseView, R.string.empty_name_message , Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }

        if (this.databaseHelper.userExists(newContact.getName()) && !this.isEdit) {
            Snackbar.make(this.baseView, R.string.not_unique_name_message , Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }

        if (!ContactValidatorHelper.isValidPhone(newContact.getPhone())) {
            Snackbar.make(this.baseView, R.string.incorrect_phone_message , Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }

        if(!ContactValidatorHelper.isValidEmail(newContact.getEmail())) {
            Snackbar.make(this.baseView, R.string.incorrect_email_message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }

        return true;
    }

    private void editContact(Contact contact) {
        this.databaseHelper.updateContact(contact);
    }

    private void addContact(Contact contact) {
        contact.setUserID(this.userId);
        this.databaseHelper.addContact(contact);
    }

    private void redirectToContactList() {
        Intent intent = new Intent(NewContactForm.this, ContactDirectory.class);
        intent.putExtra("UserID", this.userId);
        startActivity(intent);
    }

    private Contact getContactById(int id) {
        this.databaseHelper = new DBHelper(this);

        try {
            return this.databaseHelper.getContactById(id);
        } catch (SQLException e) {
            Toast.makeText(this, R.string.database_error_message, Toast.LENGTH_SHORT).show();
            return new Contact();
        }
    }
}