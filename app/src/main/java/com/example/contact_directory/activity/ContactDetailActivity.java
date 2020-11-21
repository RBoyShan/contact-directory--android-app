package com.example.contact_directory.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contact_directory.R;
import com.example.contact_directory.dialog.ConfirmDialog;
import com.example.contact_directory.entity.Contact;

import java.util.ArrayList;

public class ContactDetailActivity extends AppCompatActivity {

    private TextView name;
    private TextView phone;
    private TextView email;

    private ArrayList<Contact> contacts;
    private int currentPosition;

    private static final int ACTION = 0;

    private static final int EDIT_MENU_ITEM = Menu.FIRST;
    private static final int DELETE_MENU_ITEM = EDIT_MENU_ITEM + 1;

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
                intent.putParcelableArrayListExtra("Contacts", this.contacts);
                intent.putExtra("Index", this.currentPosition);
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

    public void okClicked() {
        this.contacts.remove(this.currentPosition);
        Intent intent = new Intent(ContactDetailActivity.this, ContactDirectory.class);
        intent.putParcelableArrayListExtra("Contacts", this.contacts);
        this.startActivity(intent);
    }

    public void cancelClicked() {
        // pass
    }
}