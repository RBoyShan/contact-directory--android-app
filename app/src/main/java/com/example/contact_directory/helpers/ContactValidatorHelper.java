package com.example.contact_directory.helpers;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactValidatorHelper {
    public static boolean isValidEmail(String email) {
        if(ContactValidatorHelper.isNullOrEmpty(email)) {
            return true;
        }

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPhone(String phone) {
        if(!TextUtils.isDigitsOnly(phone)) {
            return false;
        }

        String patterns
                = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";


        Pattern pattern = Pattern.compile(patterns);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean isNullOrEmpty(String string){
        return TextUtils.isEmpty(string);
    }
}
