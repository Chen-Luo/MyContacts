package com.chen.mycontacts.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.chen.mycontacts.R;

public class EditContactActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "EditContactActivity";

    private EditText mEditName, mEditPhoneNumber, mEditEmail;

    private ImageButton mEditCancel, mEditOk;

    private String mName, mPhoneNumber, mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        mEditName = (EditText) findViewById(R.id.edit_contact_name);
        mEditPhoneNumber = (EditText) findViewById(R.id.edit_contact_phone_number);
        mEditEmail = (EditText) findViewById(R.id.edit_contact_email);
        mEditCancel = (ImageButton) findViewById(R.id.edit_contact_cancel);
        mEditOk = (ImageButton) findViewById(R.id.edit_contact_ok);
        mEditCancel.setOnClickListener(this);
        mEditOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.edit_contact_cancel:
                setResult(RESULT_CANCELED, intent);
                finish();
                break;
            case R.id.edit_contact_ok:
                getInputInfo();
                Log.d(TAG, mName);
                Log.d(TAG, mPhoneNumber);
                Log.d(TAG, mEmail);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private boolean getPreInfo() {
        return false;
    }

    private boolean getInputInfo() {
        boolean confirm = false;
        mName = mEditName.getText().toString();
        mPhoneNumber = mEditPhoneNumber.getText().toString();
        mEmail = mEditEmail.getText().toString();
        return confirm;
    }
}
