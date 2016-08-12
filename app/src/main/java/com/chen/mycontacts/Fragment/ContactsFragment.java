package com.chen.mycontacts.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chen.mycontacts.ContactsApplication;
import com.chen.mycontacts.activity.ContactViewActivity;
import com.chen.mycontacts.activity.MainActivity;
import com.chen.mycontacts.R;
import com.chen.mycontacts.list.ContactsSearch;
import com.chen.mycontacts.util.PhotoProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends MainActivity.PlaceholderFragment {

    private static final String TAG = "ContactsFragment";

    private final Context mContext = ContactsApplication.getContext();

    private ArrayList<ContactsSearch.ContactsLookupKey> mContactsLookupKeys;

    private ContactLookupKeysAdapter mLookupKeyAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         //Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contacts, container, false);
        EditText contactSearch = (EditText) view.findViewById(R.id.contact_search_by_info);
        mContactsLookupKeys = new ContactsSearch().getLookupIdAndName();
        mLookupKeyAdapter = new ContactLookupKeysAdapter(mContext, R.layout.single_contact,
                mContactsLookupKeys);
        ListView listView = (ListView) view.findViewById(R.id.contacts_list_view);
        listView.setAdapter(mLookupKeyAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Uri lookupUri = mContactsLookupKeys.get(i).getLookupUri();
                Intent intent = new Intent(ContactsApplication.getContext(), ContactViewActivity.class);
                //intent.setAction("android.intent.action.VIEW");
               // intent.addCategory("android.intent.category.APP_CONTACTS");
                intent.setData(lookupUri);
                startActivity(intent);
            }
        });
        return view;
    }

    class ContactLookupKeysAdapter extends ArrayAdapter<ContactsSearch.ContactsLookupKey> {

        private int layoutId;

        private View view;

        private TextView contact;

        private ImageView imageView;

        public ContactLookupKeysAdapter(Context context, int layoutResourceId,
                               ArrayList<ContactsSearch.ContactsLookupKey> objects) {
            super(context, layoutResourceId, objects);
            layoutId = layoutResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ContactsSearch.ContactsLookupKey singleContact = getItem(position);
            view = LayoutInflater.from(getContext()).inflate(layoutId, null);
            contact = (TextView) view.findViewById(R.id.single_contact_view);
            imageView = (ImageView) view.findViewById(R.id.single_contact_image);
            String name = singleContact.getDisplayName();
            if(singleContact.hasPhoto()) {
                imageView.setImageBitmap(PhotoProcess.getBitmapFromUri(singleContact.getPhotoUri()));
            } else {
                imageView.setImageResource(R.drawable.contact_default_image);
            }
            contact.setText(name);
            return view;
        }
    }

}
