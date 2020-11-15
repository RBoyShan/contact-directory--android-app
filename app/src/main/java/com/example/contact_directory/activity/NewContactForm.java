package com.example.contact_directory.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.contact_directory.helpers.ContactValidatorHelper;
import com.example.contact_directory.R;
import com.example.contact_directory.helpers.UniquenessContactHelper;
import com.example.contact_directory.entity.Contact;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class NewContactForm extends AppCompatActivity {
    private View baseView;

    TextView nameField;
    TextView phoneField;
    TextView emailField;

    private ArrayList<Contact> contacts;
    private int currentIndex = -1;
    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact_form3);

        this.isEdit = false;
        this.baseView = this.findViewById(R.id.contactFormView);

        this.initActivity();
    }

    private void initActivity () {
        this.getContacts();

        if(this.getDataIndex()) {
            this.setValueData(this.contacts.get(this.currentIndex));
        }

        this.initFormFirlds();
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
            Contact contact = getNewContact();

            if(!contact.isNotEmptyObject()) {
                return;
            }

            if(this.validateForm(contact)) {
                if(this.isEdit)  {
                    this.editContact(contact);
                } else {
                    this.addContact(contact);
                }
                this.redirectToContactList();
            }
        });
    }

    private void getContacts() {
        this.contacts = getIntent().getParcelableArrayListExtra("Contacts");
    }

    private boolean getDataIndex() {
        this.currentIndex = getIntent().getIntExtra("Index", -1);

        if(this.currentIndex >= 0)
            this.isEdit = true;

        return this.isEdit;
    }

    private void initFormFirlds() {
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

    private Contact getNewContact() {
        return new Contact(
                this.nameField.getText().toString(),
                this.phoneField.getText().toString(),
                this.emailField.getText().toString(),
                Contact.idCounter
        );
    }

    private boolean validateForm(Contact contact) {
        if (ContactValidatorHelper.isNullOrEmpty(contact.getName())) {
            Snackbar.make(this.baseView, R.string.empty_name_message , Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }

        if (UniquenessContactHelper.isContactsHasName(this.contacts, contact.getName()) && !this.isEdit) {
            Snackbar.make(this.baseView, R.string.not_unique_name_message , Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }

        if (!ContactValidatorHelper.isValidPhone(contact.getPhone())) {
            Snackbar.make(this.baseView, R.string.incorrect_phone_message , Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }

        if(!ContactValidatorHelper.isValidEmail(contact.getEmail())) {
            Snackbar.make(this.baseView, R.string.incorrect_email_message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }

        return true;
    }

    private void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    private void editContact(Contact contact) {
        String newName = this.nameField.getText().toString();
        String newPhone = this.phoneField.getText().toString();
        String newEmail = this.emailField.getText().toString();

        this.contacts.get(this.currentIndex).setName(newName);
        this.contacts.get(this.currentIndex).setPhone(newPhone);
        this.contacts.get(this.currentIndex).setEmail(newEmail);
    }

    private void redirectToContactList() {
        Intent contactDirectoryIntent = new Intent(NewContactForm.this, ContactDirectory.class);
        contactDirectoryIntent.putParcelableArrayListExtra("Contacts", this.contacts);
        startActivity(contactDirectoryIntent);
    }
}