package com.example.contact_directory.entity;

import android.icu.number.IntegerWidth;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.Objects;

public class Contact implements Parcelable {
    public static int idCounter = 1;

    private String name;
    private String phone;
    private String email;
    private final int id;

    public Contact() {
        this("", "", "", Contact.idCounter);
    }

    public Contact(String name, String phone, String email, int id) {
        Contact.idCounter++;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.id = id;
    }

    public Contact(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);
        this.name = data[0];
        this.phone = data[1];
        this.email = data[2];
        this.id = Integer.parseInt(data[3]);
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public boolean isNotEmptyObject() {
        return !TextUtils.isEmpty(this.phone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id == contact.id &&
                name.equals(contact.name) &&
                phone.equals(contact.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {
                this.name,
                this.phone,
                this.email,
                String.valueOf(this.id)
        });
    }
}
