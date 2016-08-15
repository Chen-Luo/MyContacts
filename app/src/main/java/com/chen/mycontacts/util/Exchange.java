package com.chen.mycontacts.util;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.chen.mycontacts.ContactsApplication;

/**
 * Created by peng.cheng on 2016/8/15.
 */
public class Exchange {

    private static final String TAG = "Exchange";

    public static String getNameFromUri(String uriString, int type) {
        String name = "默认";
        Cursor cursor = null;
        if(uriString == null) {
            return name;
        }
        Uri uri = Uri.parse(uriString);
        switch (type) {
            case 0:
                try {
                    cursor = ContactsApplication.getContext().getContentResolver().query(
                            uri, new String [] {MediaStore.Audio.Media.DISPLAY_NAME },
                            null, null, null);
                    if(cursor != null && cursor.moveToFirst()) {
                        name = cursor.getString(0);
                    }
                    Log.d(TAG, name);
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                } finally {
                    if(cursor != null) {
                        cursor.close();
                    }
                }
                break;
            default:
                break;
        }
        if ((name != null) && (name.length() > 0)) {
            int dot = name.lastIndexOf('.');
            if ((dot >-1) && (dot < (name.length()))) {
                name = name.substring(0, dot);
            }
        }
        return name;
    }
}
