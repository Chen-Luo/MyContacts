package com.chen.mycontacts.list;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.chen.mycontacts.ContactsApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by peng.cheng on 2016/8/12.
 */
public class ContactsSearch {

    private static final String TAG = "ContactsSearch";

    public ArrayList<ContactsLookupKey> getLookupIdAndName() {
        Cursor cursor = null;
        ArrayList<ContactsLookupKey> contactsLookupKeyArrayList =
                new ArrayList<ContactsLookupKey>();
        try {
            final ContentResolver resolver = ContactsApplication.getContext().getContentResolver();
            Uri uri = ContactsContract.Contacts.CONTENT_URI;
            cursor = resolver.query(uri, new String[]{
                            ContactsContract.Contacts._ID,
                            ContactsContract.Contacts.LOOKUP_KEY,
                            ContactsContract.Contacts.DISPLAY_NAME,
                            ContactsContract.Contacts.PHOTO_ID,
                            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI},
                    null, null, ContactsContract.Contacts.SORT_KEY_ALTERNATIVE);
            if (cursor != null && cursor.moveToFirst()) {                                           //注意&&使用
                long _id;
                String lookupKey;
                String displayName;
                long photoId;
                String photoThumbnailUri;
                do {
                    _id = cursor.getLong(0);
                    lookupKey = cursor.getString(1);
                    displayName = cursor.getString(2);
                    photoId = cursor.getLong(3);
                    photoThumbnailUri = cursor.getString(4);
                    ContactsLookupKey contactsLookupKey =
                            new ContactsLookupKey(_id, lookupKey, displayName, photoId, photoThumbnailUri);
                    contactsLookupKeyArrayList.add(contactsLookupKey);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            return contactsLookupKeyArrayList;
        }
    }

    public class ContactsLookupKey {
        private long mId;
        private String mLookup;
        private String mDisplayName;
        private long mPhotoId;
        private String mPhotoThumbnailUri;

        ContactsLookupKey(long id, String lookup, String displayName, long photoId, String photoUri) {
            mId = id;
            mLookup = lookup;
            mDisplayName = displayName;
            mPhotoId = photoId;
            mPhotoThumbnailUri = photoUri;
        }

        public long getId() { return mId; }

        public String getLookup() { return mLookup; }

        public String getDisplayName() { return mDisplayName; }

        public boolean hasPhoto() { return ( mPhotoId > 0); }

        public Uri getPhotoUri() { return hasPhoto() ? Uri.parse(mPhotoThumbnailUri) : null;}

        public Uri getLookupUri() {
            return ContactsContract.Contacts.getLookupUri(mId, mLookup);
        }
    }
}
