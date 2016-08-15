package com.chen.mycontacts.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chen.mycontacts.R;
import com.chen.mycontacts.util.Exchange;
import com.chen.mycontacts.util.PhotoProcess;

import java.util.ArrayList;
import java.util.List;


public class ContactViewActivity extends AppCompatActivity {

    private static final String TAG = "ContactViewActivity";

    private static final String DISPLAY_NAME = "display_name";
    private static final String HAS_PHONE_NUMBER = "has_phone_number";
    private static final String _ID = "_id";
    private static final String CUSTOM_RINGTONE = "custom_ringtone";
    private static final String PHOTO_URI = "photo_uri";
    private static final String IN_VISIBLE_GROUP = "in_visible_group";
    private static final int TYPE_PHONE_NUMBER = 100;
    private static final int TYPE_RINGTONE = 101;
    private static final int TYPE_GROUP = 102;

    private long mId;
    private String mDisplayName;
    private long mHasPhoneNumber;
    private String mPhotoUri;
    private String mCustomRingtone;
    private long mIsVisibleGroup;
    private List<String> mPhoneNumbers;
    private ImageView mImageView;
    private TextView mNameView;
    private ListView mInfoView;
    private ArrayList<ListSingleElement> contactElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);
        Intent intent = getIntent();
        long _id = intent.getLongExtra("_id", -1);
        String lookupKey = intent.getStringExtra("lookup");
        Uri lookupUri = ContactsContract.Contacts.getLookupUri(_id, lookupKey);
        mImageView= (ImageView) findViewById(R.id.contact_view_image);
        mNameView = (TextView) findViewById(R.id.contact_view_display_name);
        mInfoView = (ListView) findViewById(R.id.contact_info_list_view);
        if(_id > 0) {
            getInfoForContact(lookupUri);
        } else {
            Log.e(TAG, "no contact has this id");
        }
    }

    private void getInfoForContact(Uri lookupUri) {
        Cursor cursor = null;
        contactElements = new ArrayList<ListSingleElement>();
        try {
            cursor = getContentResolver().query(lookupUri, new String[] {
                    _ID, DISPLAY_NAME, PHOTO_URI, HAS_PHONE_NUMBER, CUSTOM_RINGTONE,
                    IN_VISIBLE_GROUP },
                    null, null, null);
            if(cursor != null && cursor.moveToFirst()) {
                mId = cursor.getLong(0);
                mDisplayName = cursor.getString(1);
                mPhotoUri = cursor.getString(2);
                mHasPhoneNumber = cursor.getLong(3);
                mCustomRingtone = cursor.getString(4);
                mIsVisibleGroup = cursor.getLong(5);
                if(mHasPhoneNumber > 0) {
                    Cursor phoneCursor = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + "=" + mId, null, null);
                    if(phoneCursor.moveToFirst()) {
                        mPhoneNumbers = new ArrayList<String>();
                        do {
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            contactElements.add(new ListSingleElement(phoneNumber, "中国移动", TYPE_PHONE_NUMBER));
                            mPhoneNumbers.add(phoneNumber);
                        } while (phoneCursor.moveToNext());
                    }
                }
                contactElements.add(new ListSingleElement("NULL", "群组", TYPE_GROUP));
                contactElements.add(new ListSingleElement(
                        Exchange.getNameFromUri(mCustomRingtone, 0), "铃声", TYPE_RINGTONE));
            }
            if(mPhotoUri != null) {
                mImageView.setImageBitmap(PhotoProcess.getBitmapFromUri(Uri.parse(mPhotoUri)));
            } else {
                mImageView.setImageResource(R.drawable.contact_default_image);
            }
            mNameView.setText(mDisplayName);
            ListElementsAdapter adapter = new ListElementsAdapter(ContactViewActivity.this,
                    R.layout.listview_two_lines, contactElements);
            mInfoView.setAdapter(adapter);
            mInfoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ListSingleElement listSingleElement = contactElements.get(i);
                    switch (listSingleElement.getType()) {
                        case TYPE_PHONE_NUMBER:
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + listSingleElement.getContent()));
                            PackageManager pm = getPackageManager();
                            boolean permission = (PackageManager.PERMISSION_GRANTED ==
                                    pm.checkPermission("android.permission.CALL_PHONE", "packageName"));
                            if (permission) {
                                startActivity(intent);
                            } else {
                                Log.d(TAG, "no permission");
                            }
                            break;
                        case TYPE_GROUP:
                            break;
                        case TYPE_RINGTONE:
                            break;
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private class ListElementsAdapter extends ArrayAdapter<ListSingleElement> {

        private int layoutId;

        public ListElementsAdapter(Context context, int layoutResourceId,
                                   ArrayList<ListSingleElement> objects) {
            super(context, layoutResourceId, objects);
            layoutId = layoutResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListSingleElement listSingleElement = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(layoutId, null);
            TextView content = (TextView) view.findViewById(R.id.contact_element_content);
            TextView description = (TextView) view.findViewById(R.id.contact_element_description);
            content.setText(listSingleElement.getContent());
            description.setText(listSingleElement.getDescription());
            return view;
        }


    }

    private class ListSingleElement {
        String content;
        String description;
        int type;

        ListSingleElement(String content, String description, int type) {
            this.content = content;
            this.description = description;
            this.type = type;
        }

        public String getContent() { return content; }
        public String getDescription() { return description; }
        public int getType () { return type; }
    }
}
