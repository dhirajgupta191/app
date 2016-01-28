package com.example.laptop.status.Fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.laptop.status.Adapter.ContactListAdapter;
import com.example.laptop.status.AsyncResultInterface;
import com.example.laptop.status.Connectivity.DataBaseServer;
import com.example.laptop.status.Connectivity.SharePreference;
import com.example.laptop.status.R;
import com.example.laptop.status.ToastMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    View view;
    MatrixCursor contactMatrixCursor;
    DataBaseServer dataBaseServer;
    SharePreference sharePreference;
    ContactListAdapter contactListAdapter;
    String[] contact_no, contact_name,contact_status;
    RecyclerView contact_recycler_view;

    public static ContactFragment getInstance(int position) {
        ContactFragment fragmentContact = new ContactFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragmentContact.setArguments(args);
        return fragmentContact;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        this.view = view;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contact_recycler_view = (RecyclerView) view.findViewById(R.id.contact_recycler_view);
        Cursor contactListCursor = null;
        int idForMatrixCursor = -1;
        String duplicate_number = "", new_number = "";
        List<String> add_contact_number = new ArrayList<>();
        String contactNo = "", contactName = "", countryCode = "";
        sharePreference = new SharePreference();
        countryCode = sharePreference.getValue(getActivity().getBaseContext(), SharePreference.COUNTRY_CODE);
        try {

            ContentResolver contentResolver = getActivity().getContentResolver();
            contactMatrixCursor = new MatrixCursor(new String[]{"_id", "DISPLAY_NAME", "PHONE_NUMBER"});

            // requested columns
            String[] requestedColumns = {ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};

            // save the contacts into the cursor.
            contactListCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, requestedColumns, null, null, null, null);

            /*check the contacts with the DATABASE SQLITE.
            * show those contacts which are present in database sqlite and in contactListCursor. */
            while (contactListCursor.moveToNext()) {
                // remove all the special character and space from the contact list.
                String contact_number = contactListCursor.getString(2);
                String contact_name = contactListCursor.getString(1);
                contact_number = contact_number.replaceAll("[( )-]", "");
                if (contact_number.length() > 9) {
                    new_number = contact_number;

                    switch (contact_number.length()) // check the length of the contact number...
                    {
                        case 10:
                            // only number, then add country code
                            if (!new_number.equals(duplicate_number)) {
                                if (contactNo.equals("")) {
                                    contactName = contactListCursor.getString(1);
                                    contactNo = countryCode + new_number;
                                } else {
                                    contactName = contactName + ",ht5DsT," + contactListCursor.getString(1);
                                    contactNo = contactNo + "," + countryCode + new_number;
                                }
                                duplicate_number = new_number;
                            }
                            break;
                        case 11:
                            // if zero is present, then omit zero & add country code
                            if (!new_number.equals(duplicate_number)) {
                                String first_digit = contact_number.substring(0, 1);
                                if (first_digit.equals("0")) {
                                    if (contactNo.equals("")) {
                                        contactName = contactListCursor.getString(1);
                                        contactNo = countryCode + new_number.substring(1, new_number.length());
                                    } else {
                                        contactName = contactName + ",ht5DsT," + contactListCursor.getString(1);
                                        contactNo = contactNo + "," + countryCode + new_number.substring(1, new_number.length());
                                    }
                                }
                                duplicate_number = new_number;
                            }
                            break;
                        case 12:
                            // 91-9090808090
                            // no need to add country code
                            if (!new_number.equals(duplicate_number)) {
                                if (contactNo.equals("")) {
                                    contactName = contactListCursor.getString(1);
                                    contactNo = new_number;
                                } else {
                                    contactName = contactName + ",ht5DsT," + contactListCursor.getString(1);
                                    contactNo = contactNo + "," + new_number;
                                }
                                duplicate_number = new_number;
                            }
                            break;
                        case 13:
                            // +91
                            // no need to add country code
                            if (!new_number.equals(duplicate_number)) {
                                if (contactNo.equals("")) {
                                    contactName = contactListCursor.getString(1);
                                    contactNo = new_number;
                                } else {
                                    contactName = contactName + ",ht5DsT," + contactListCursor.getString(1);
                                    contactNo = contactNo + "," + new_number;
                                }
                                duplicate_number = new_number;
                            }
                            break;
                    }


                        /*contactMatrixCursor.newRow().add(++idForMatrixCursor).add(contactListCursor.getString(1)).add(contact_number);

                        add_contact_number.add("," + new_number);*/


                }

            }

            String method = "contact_numbers";
            dataBaseServer = new DataBaseServer(getActivity());
            dataBaseServer.SetOnAsyncResult(asyncResult);
            dataBaseServer.execute(method, contactNo, contactName);

//             // desired columns from contactListCursor.
//            String[] columns = {"DISPLAY_NAME","PHONE_NUMBER"};
//            int[] to = {R.id.contactName, R.id.contactPhoneNo};
//
//            // created cursor adapter using the cursor pointing to the desire data as well as the layout information
//            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.inflate_contact_list, contactMatrixCursor, columns, to, 0);
//            listViewContact.setAdapter(simpleCursorAdapter);


        } catch (Exception error) {
            ToastMessage.showLogMessages(error.toString());
        }
    }



    AsyncResultInterface asyncResult = new AsyncResultInterface() {
        @Override
        public void OnResultSuccess(String message) {
            try {
                JSONObject jsonObject = new JSONObject(message);
                String ok = jsonObject.getString("status");
                if (ok.equals("1")) {
                    // get the message object
                    String msg = jsonObject.getString("msg");
                    JSONObject jsonObject1 = new JSONObject(msg);
                    String name = jsonObject1.getString("name");
//                    String number = jsonObject1.getString("phone_no");
                    String status = jsonObject1.getString("contact_status");

                    contact_name = name.split(",");
//                    contact_no = number.split(",");
                    contact_status = status.split(",");

                    contactListAdapter = new ContactListAdapter(getActivity(), contact_name,contact_status);
                    contact_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false));
                    contact_recycler_view.setAdapter(contactListAdapter);

                    /*listViewContact.setAdapter(contactListAdapter);*/

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void OnResultSuccess(int flag, String server_id) {

        }

        @Override
        public void OnResultSuccess() {

        }

        @Override
        public void OnResultFail() {

        }
    };

}


