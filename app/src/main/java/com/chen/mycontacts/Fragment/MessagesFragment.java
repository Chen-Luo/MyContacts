package com.chen.mycontacts.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.mycontacts.activity.MainActivity;
import com.chen.mycontacts.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends MainActivity.PlaceholderFragment {


    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

}
