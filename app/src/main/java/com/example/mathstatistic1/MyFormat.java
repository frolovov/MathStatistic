package com.example.mathstatistic1;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

public class MyFormat {
    public static String commaToDot(String string) {
        String resultString = "";
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ',') {
                resultString += ".";
            } else {
                resultString += string.charAt(i);
            }
        }
        return resultString;
    }
}
