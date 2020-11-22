package com.example.contact_directory.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.contact_directory.entity.Contact;
import com.example.contact_directory.entity.User;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    //DB Config
    public static final String DB_NAME = "AppDB.db";
    private static final int DB_VERSION = 1;

    //Tables
    private static final String TABLE_USERS = "users";
    private static final String TABLE_CONTACTS = "contacts";

    //Queries
    private static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + TABLE_USERS + "(\n" +
            "id       INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
            "username TEXT (50), \n" +
            "password TEXT (50)" +
            ");";

    private static final String CREATE_CONTACTS_TABLE =
            "CREATE TABLE " + TABLE_CONTACTS + "(\n" +
            "id      INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
            "user_id INTEGER NOT NULL, \n" +
            "name    TEXT (100) NOT NULL, \n" +
            "phone   TEXT (20) NOT NULL, \n" +
            "email   TEXT (50), \n" +
            "FOREIGN KEY(user_id) REFERENCES " + TABLE_USERS + "(id) \n" +
            ");";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    private static final String SELECT_ALL = "SELECT * FROM ";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_USERS_TABLE);
        database.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL(DROP_TABLE + TABLE_USERS);
        database.execSQL(DROP_TABLE + TABLE_CONTACTS);

        this.onCreate(database);
    }

    public ArrayList<Contact> getContacts(int uid) throws SQLException {
        String sqlQuery = SELECT_ALL + TABLE_CONTACTS + " WHERE user_id = ?";
        ArrayList<Contact> contacts = new ArrayList<>();

        try {
            SQLiteDatabase database = this.getReadableDatabase();

            @SuppressLint("Recycle")
            Cursor cursor = database.rawQuery(sqlQuery, new String[]{ Integer.toString(uid) });

            if (cursor.moveToFirst()) {
                do {
                    int id = Integer.parseInt(cursor.getString(0));
                    int user_id = Integer.parseInt(cursor.getString(1));
                    String name = cursor.getString(2);
                    String phone = cursor.getString(3);
                    String email = cursor.getString(4);

                    Contact contact = new Contact(name, phone, email, id);
                    contact.setUserID(user_id);

                    contacts.add(contact);
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();
        } catch (SQLException e) {
            throw new SQLException();
        }

        return contacts;
    }

    public Contact getContactById(int id) throws SQLException {
        String sqlQuery = SELECT_ALL + TABLE_CONTACTS + " WHERE id = ?";
        Contact contact = new Contact();

        try {
            SQLiteDatabase database = this.getReadableDatabase();

            @SuppressLint("Recycle")
            Cursor cursor = database.rawQuery(sqlQuery, new String[]{ Integer.toString(id) });

            if (cursor.moveToFirst()) {
                int user_id = Integer.parseInt(cursor.getString(1));
                String name = cursor.getString(2);
                String phone = cursor.getString(3);
                String email = cursor.getString(4);

                contact = new Contact(name, phone, email, id);
                contact.setUserID(user_id);
            }
            cursor.close();
            database.close();
        } catch (SQLException e) {
            throw new SQLException();
        }

        return contact;
    }

    public ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();

        try {
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.query(TABLE_USERS,
                    new String[] {"id", "username", "password"},
                    null, null, null, null,
                    "username");

            while(cursor.moveToNext()) {
                users.add(
                        new User(
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2)
                        )
                );
            }
            cursor.close();
            database.close();
        } catch (SQLException e) {
            throw new SQLException();
        }

        return users;
    }

    public User getUserById(int id) throws SQLException {
        String sqlQuery = SELECT_ALL + TABLE_USERS + " WHERE id = ?";
        User user = new User();

        try {
            SQLiteDatabase database = this.getReadableDatabase();

            @SuppressLint("Recycle")
            Cursor cursor = database.rawQuery(sqlQuery, new String[]{ Integer.toString(id) });

            if (cursor.moveToFirst()) {
                int uid = Integer.parseInt(cursor.getString(0));
                String username = cursor.getString(1);
                String password = cursor.getString(2);

                user = new User(uid,username, password);
            }
            cursor.close();
            database.close();
        } catch (SQLException e) {
            throw new SQLException();
        }

        return user;
    }

    public void addContact(Contact contact) {
        ContentValues values = new ContentValues();

        values.put("user_id", contact.getUserID());
        values.put("name", contact.getName());
        values.put("phone", contact.getPhone());
        values.put("email", contact.getEmail());

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(TABLE_CONTACTS, null, values);
    }

    public void updateContact(Contact contact) {
        ContentValues values = new ContentValues();

        values.put("id", contact.getId());
        values.put("user_id", contact.getUserID());
        values.put("name", contact.getName());
        values.put("phone", contact.getPhone());
        values.put("email", contact.getEmail());

        SQLiteDatabase database = this.getWritableDatabase();
        database.update(TABLE_CONTACTS, values, "id = ?", new String[]{String.valueOf(contact.getId())});
    }

    public void deleteContact(int id) {
        try {
            SQLiteDatabase database = this.getReadableDatabase();
            database.delete(TABLE_CONTACTS, "id = ?", new String[] {Integer.toString(id)});
            database.close();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public void addUser(User user) {
        ContentValues values = new ContentValues();

        values.put("username", user.getUsername());
        values.put("password", user.getPassword());

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(TABLE_USERS, null, values);
    }

    public void updateUser(User user) {
        ContentValues values = new ContentValues();

        values.put("id", user.getId());
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());

        SQLiteDatabase database = this.getWritableDatabase();
        database.update(TABLE_USERS, values, "id = ?", new String[]{String.valueOf(user.getId())});
    }

    public boolean userExists(String username) {
        String sqlQuery = SELECT_ALL + TABLE_USERS + " WHERE username = ?" ;

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(sqlQuery, new String[] { username });

        return cursor.moveToFirst();
    }

    public boolean checkUserPassword(String username, String password) {
        String sqlQuery = SELECT_ALL + TABLE_USERS + " WHERE username = ?  AND password = ?";

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(sqlQuery, new String[] { username, password });

        return cursor.moveToFirst();
    }

    public int getUserId(String username) {
        String sqlQuery = "SELECT id from " + TABLE_USERS + " WHERE username = ?";

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(sqlQuery, new String[] { username });

        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        }

        return -1;
    }
}
