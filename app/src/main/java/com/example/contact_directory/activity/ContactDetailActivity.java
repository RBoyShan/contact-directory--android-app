package com.example.contact_directory.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contact_directory.DB.DBHelper;
import com.example.contact_directory.R;
import com.example.contact_directory.dialog.ConfirmDialog;
import com.example.contact_directory.entity.Contact;

import java.util.ArrayList;

public class ContactDetailActivity extends AppCompatActivity {
    private TextView name;
    private TextView phone;
    private TextView email;

    private Contact contact;
    private int contactId;

    private static final int ACTION = 0;

    private static final int EDIT_MENU_ITEM = Menu.FIRST;
    private static final int DELETE_MENU_ITEM = EDIT_MENU_ITEM + 1;

    private DBHelper databaseHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        this.initActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu actionMenu = menu.addSubMenu("Contact");
        actionMenu.add(ACTION, EDIT_MENU_ITEM, 0, R.string.menu_option_edit);
        actionMenu.add(ACTION, DELETE_MENU_ITEM, 1, R.string.menu_option_delete);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case EDIT_MENU_ITEM:
                Intent intent = new Intent(ContactDetailActivity.this, NewContactForm.class);
                intent.putExtra("UserID", this.userId);
                intent.putExtra("ContactID", this.contactId);
                this.startActivity(intent);
                break;
            case DELETE_MENU_ITEM:
                FragmentManager manager = getSupportFragmentManager();
                ConfirmDialog dialog = new ConfirmDialog();
                dialog.show(manager, "myDialog");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initActivity() {
        this.getData();
        this.initViews();
        this.contact = this.getContactById(this.contactId);
        this.setData();
        this.initBackButton();
    }

    private void initViews() {
        this.name = this.findViewById(R.id.contactDetailName);
        this.phone = this.findViewById(R.id.contactDetailPhone);
        this.email = this.findViewById(R.id.contactDetailEmail);
    }

    private void getData() {
        Intent intent = getIntent();
        if(intent.hasExtra("ContactID") && intent.hasExtra("UserID")) {
            this.contactId = intent.getIntExtra("ContactID", -1);
            this.userId = intent.getIntExtra("UserID", -1);
        } else {
            Toast.makeText(this, "No contact", Toast.LENGTH_LONG).show();
        }
    }

    private void setData() {
        this.name.setText(this.contact.getName());
        this.phone.setText(this.contact.getPhone());
        this.email.setText(this.contact.getEmail());
    }

    private void initBackButton() {
        this.findViewById(R.id.contactDetailBackButton).setOnClickListener(view -> {
            onBackPressed();
        });
    }

    public void okClicked() {
        this.databaseHelper.deleteContact(this.contactId);
        Intent intent = new Intent(ContactDetailActivity.this, ContactDirectory.class);
        intent.putExtra("UserID", this.userId);
        this.startActivity(intent);
    }

    public void cancelClicked() {
        // pass
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