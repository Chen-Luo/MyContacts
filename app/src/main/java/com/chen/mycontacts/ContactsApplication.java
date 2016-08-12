package com.chen.mycontacts;

import android.app.Application;
import android.content.Context;

/**
 * Created by peng.cheng on 2016/8/12.
 */
public class ContactsApplication extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
    public static Context getContext() {
        return mContext;
    }
}
