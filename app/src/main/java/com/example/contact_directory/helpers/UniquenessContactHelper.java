package com.example.contact_directory.helpers;

import com.example.contact_directory.entity.Contact;

import java.util.ArrayList;

public class UniquenessContactHelper {
    public static boolean isContactsHasName(ArrayList<Contact> contacts, String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }
}
