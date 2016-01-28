package com.example.laptop.status.Fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop.status.Adapter.StatusAdapter;
import com.example.laptop.status.AsyncResultInterface;
import com.example.laptop.status.Bean.StatusBean;
import com.example.laptop.status.Bean.TestBean;
import com.example.laptop.status.Connectivity.DataBaseSQLite;
import com.example.laptop.status.Connectivity.DataBaseServer;
import com.example.laptop.status.GMmodel.StatusModel;
import com.example.laptop.status.MainActivity;
import com.example.laptop.status.R;
import com.example.laptop.status.ToastMessage;
import com.example.laptop.status.values.RobotoEditView;
import com.example.laptop.status.values.RobotoTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment implements View.OnClickListener {

    DataBaseSQLite dataBaseSQLite;
    DataBaseServer dataBaseServer;
    Cursor cursor;
    SimpleCursorAdapter simpleCursorAdapter;
    ListView listView;
    /* get the all variable names*/
    ImageView sendImageView, editImageView;
    TextView currentStatusTextView, postedStatusTimeTextView;
    FloatingActionButton fab_edit,fab_send;
    EditText sendEditTextView;
    View view;
    RobotoEditView status_edit_text;
    RobotoTextView status_text,status_time;


    RecyclerView recycler_view;
    CoordinatorLayout.LayoutParams fab_send_params,fab_edit_params;
    boolean  fab_edit_button_clicked = false;

    ArrayList<StatusBean> arrayList;
//    ArrayList<TestBean> arrayList;
    StatusAdapter statusAdapter;
    TestBean testBean;

    StatusModel statusModel;
    public static final String NAME="status_fragment";

    // get the list view item into array List..
    // when list view is clicked, the clicked item get selected and placed into current Text View.
    ArrayList<String> arrayListContainMyStatusText = new ArrayList<String>();
    ArrayList<String> arrayListContainMyStatusID = new ArrayList<String>();

    public static StatusFragment getInstance(int position , String Name) {
        StatusFragment fragmentStatus = new StatusFragment();
        Bundle args = new Bundle();
        args.putString(NAME,Name);
        args.putInt("position", position);
        fragmentStatus.setArguments(args);

        return fragmentStatus;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_status, container, false);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        status_text = (RobotoTextView) view.findViewById(R.id.status_text);
        status_time = (RobotoTextView) view.findViewById(R.id.status_time);
        status_edit_text = (RobotoEditView) view.findViewById(R.id.status_edit_text);

        fab_edit = (FloatingActionButton) view.findViewById(R.id.fab_edit);
        fab_edit.setOnClickListener(this);
//        fab_edit_params = (CoordinatorLayout.LayoutParams) fab_edit.getLayoutParams();

        /*fab_send = (FloatingActionButton) view.findViewById(R.id.fab_send);
        fab_send.setOnClickListener(this);
        fab_send_params = (CoordinatorLayout.LayoutParams) fab_send.getLayoutParams();
        fab_send_params.setAnchorId(View.NO_ID);
        fab_send.setLayoutParams(fab_send_params);
        fab_send.setVisibility(View.GONE);*/

        arrayList = new ArrayList<>();
        statusModel = new StatusModel().getInstance();
        getListView();

       /* String time[] = {"1","2","3","4","1","2","3","4"};
        String status[] = {"1","2","3","4","1","2","3","4"};
        for(int i =0; i<status.length;i++)
        {
            testBean = new TestBean();
            testBean.time = time[i];
            testBean.status = status[i];
            arrayList.add(testBean);
        }*/
/*
        statusAdapter = new StatusAdapter(getActivity().getBaseContext(), arrayList);

        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(statusAdapter);*/
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
/*

        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        getListView();
        recycler_view.setAdapter(statusAdapter);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
*/


    }

//        @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
////        try {
//            dataBaseSQLite = new DataBaseSQLite(getActivity());
//            currentStatusTextView = (TextView) view.findViewById(R.id.currentStatusTextView);
//            postedStatusTimeTextView = (TextView) view.findViewById(R.id.postedStatusTimeTextView);
//
////             get the current status..
//            cursor = dataBaseSQLite.getCurrentStatus(dataBaseSQLite);
//            if (cursor.moveToFirst()) {
//
//                currentStatusTextView.setText("");
//                // get the time when status text was posted...
//                String getPostedTime = cursor.getString(cursor.getColumnIndex(dataBaseSQLite.TIME));
//                String postedTime = isBetween(getPostedTime);
//
//                postedStatusTimeTextView.setText(postedTime);
//                // set the current status into currentStatusTextView
//                currentStatusTextView.setText(cursor.getString(cursor.getColumnIndex(dataBaseSQLite.MY_STATUS_TEXT)));
//
//            } else {
//                // set the current status to default value.. no need to add any code here...
//            }
////         get the id of the buttons text, edit, listview
//            editImageView = (ImageView) view.findViewById(R.id.imageViewEdit);
//            sendImageView = (ImageView) view.findViewById(R.id.imageViewSend);
//            sendEditTextView = (EditText) view.findViewById(R.id.sendEditText);
//            listView = (ListView) view.findViewById(R.id.listView);
////         showing the status in the listView...
//            getListView();
//
////             on clicking list view item, set the current text view to the clicked item
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    String text = arrayListContainMyStatusText.get(position);
//                    currentStatusTextView.setText(text);
//                    // get the status id.
//                    String Id = arrayListContainMyStatusID.get(position);
//                    ToastMessage.showLogMessages(text + " id = " + id + " , position= " + position);
//                    // this will make all the status id to zero
//                    dataBaseSQLite.changeCurrentStatus(dataBaseSQLite);
//                    // this will make selected status id to one
//                    dataBaseSQLite.changeCurrentStatus(dataBaseSQLite, Id);
//
//                    sendImageView.setVisibility(View.GONE);
//                    sendEditTextView.setVisibility(View.GONE);
//                    editImageView.setVisibility(View.VISIBLE);
//                    currentStatusTextView.setVisibility(View.VISIBLE);
//                }
//            });
//
//
////         call this function to open and close the keyboard
//            sendEditTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if (hasFocus) {
//                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
//                        inputMethodManager.showSoftInput(sendEditTextView, 0);
//                    } else {
//                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
//                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                    }
//                }
//
//            });
//
//        }
//        catch (Exception error)
//        {
//            Log.e("error", error.toString());
//        }
//
//    }

    public void getListView() {
//        // clear the listView , arraylistcontain my status id, array list contain my status text every time...
//        listView.setAdapter(null);
//        arrayListContainMyStatusID.clear();
//        arrayListContainMyStatusText.clear();

        recycler_view.setAdapter(null);
        arrayList.clear();

        dataBaseSQLite = new DataBaseSQLite(getActivity());
        cursor = dataBaseSQLite.getStatusTable(dataBaseSQLite);
        if (cursor.moveToFirst()) {
            do {
//                arrayListContainMyStatusID.add(cursor.getString(cursor.getColumnIndex(dataBaseSQLite.ID)));
//                arrayListContainMyStatusText.add(cursor.getString(cursor.getColumnIndex(dataBaseSQLite.MY_STATUS_TEXT)));
                StatusBean statusBean = new StatusBean();
                statusBean.status_text = cursor.getString(cursor.getColumnIndex(DataBaseSQLite.MY_STATUS_TEXT));
                statusBean.time =  cursor.getString(cursor.getColumnIndex(DataBaseSQLite.TIME));
                statusBean.current_status = cursor.getString(cursor.getColumnIndex(DataBaseSQLite.CURRENT_STATUS));
                arrayList.add(statusBean);


            } while (cursor.moveToNext());
        }
//            String[] columns = {dataBaseSQLite.ID, dataBaseSQLite.MY_STATUS_TEXT};
//            int[] to = {R.id._ID, R.id.status_text};
//            simpleCursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.fragment_status_listview, cursor, columns, to, 0);
//            listView.setAdapter(simpleCursorAdapter);

//        recycler_view.setAdapter(statusAdapter);


        statusAdapter = new StatusAdapter(getActivity().getBaseContext(), arrayList);

        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(statusAdapter);


    }

    /*AsyncResultInterface asyncResultInterface = new AsyncResultInterface() {


        @Override
        public void OnResultSuccess(String message) {

        }

        @Override
        public void OnResultSuccess(int flag, String server_id) {
            try {
                // insert status successfully...
                if (flag == 2) {
                    // call to make currentstatus from 1 to 0
                    dataBaseSQLite.changeCurrentStatus(dataBaseSQLite);
                    // insert into my_status table
                    // get time
                    String time = getDateTime();
                    dataBaseSQLite.insertIntoStatusTable(dataBaseSQLite, server_id, sendEditTextView.getText().toString(), time);
                    currentStatusTextView.setText("");
                    currentStatusTextView.setText(sendEditTextView.getText().toString());
                    getListView();
                    ToastMessage.showLogMessages("successfully insert status...!!");
                }
            } catch (Exception error) {
                ToastMessage.showLogMessages(error.toString());
            }
        }

        @Override
        public void OnResultSuccess() {
            // upload profile pic
        }

        @Override
        public void OnResultFail() {
            ToastMessage.showLogMessages("not successful");
        }
    };*/


    public String getDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
        return date.format(calendar.getTime());
    }

    public String isBetween(String getPostedTime) {
        String postTime = "";
        try {
            /* get Posted time. getPostedTime is the time when status was posted.
            * getPostedTimeReplace is after replacing the AM, -, :,*/
            String getPostedTimeReplace = getPostedTime.replaceAll("[a-zA-Z-: ]", "");
            Long getPostedTimeLong = Long.parseLong(getPostedTimeReplace);
            // get the current time
            String getCurrentTime = getDateTime();
            String getCurrentTimeReplace = getCurrentTime.replaceAll("[a-zA-Z-: ]", "");
            Long getCurrentTimeLong = Long.parseLong(getCurrentTimeReplace);

            Long number = getCurrentTimeLong - getPostedTimeLong;

            /*get the hours time from both the postedTime and currentTime.*/
            String getPostedTimeHours = getPostedTimeReplace.substring(8, 10);
            String getCurrentTimeHours = getCurrentTimeReplace.substring(8, 10);
            /*get the minutes time from both the postedTime and CurrentTime.*/
            String getPostedTimeMinutes = getPostedTimeReplace.substring(10, getPostedTimeReplace.length());
            String getCurrentTimeMinutes = getCurrentTimeReplace.substring(10, getCurrentTimeReplace.length());

            if (number < 99) {
                // status posted few minutes ago...
                if (getPostedTimeHours.equals(getCurrentTimeHours)) {
                    postTime = number + " minutes ago";
                } else {
                    postTime = String.valueOf((60 - Integer.parseInt(getPostedTimeMinutes)) + Integer.parseInt(getCurrentTimeMinutes)) + " minutes ago";
                }
            } else if (number < 2359 && number > 100) {
                // status posted few hours ago...
                number = number / 100;
                postTime = number + " hours ago";
            } else if (number < 12359 && number > 2359) {
                // status posted yesterday...
                postTime = "yesterday at " + getPostedTime.substring(10, getPostedTime.length());
            } else {
                // status posted few days ago...
                postTime = getPostedTime;
            }
        } catch (StringIndexOutOfBoundsException error) {
            ToastMessage.showLogMessages(error.toString());
        }
        return postTime;

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id)
        {
            case R.id.fab_edit:
                if(fab_edit_button_clicked == false) {
                    status_text.setVisibility(View.GONE);
                    status_time.setVisibility(View.INVISIBLE);
                    status_edit_text.setText("");
                    status_edit_text.setVisibility(View.VISIBLE);
                    fab_edit.setImageResource(R.drawable.ic_send_white);
                    fab_edit_button_clicked = true;
                    ((MainActivity)getActivity()).fabClickedTrue();
                }
                else
                {
                    // 1. check whether edit box is empty or not
                    SendStatus();
                    fab_edit.setImageResource(R.drawable.ic_edit);
                    fab_edit_button_clicked = false;
                    ((MainActivity)getActivity()).fabClickedFalse();
                }


                break;
        }
    }

    public void SendStatus()
    {
        if(status_edit_text.getText().toString().trim().length() > 0)
        {
            // edit box is not empty
            String method = "insert_status";
            dataBaseServer = new DataBaseServer(getActivity());
            dataBaseServer.SetOnAsyncResult(asyncResult);
            dataBaseServer.execute(method,status_edit_text.getText().toString());

            status_text.setVisibility(View.VISIBLE);
            status_time.setVisibility(View.VISIBLE);
            status_edit_text.setVisibility(View.GONE);

        }
        else
        {
            // edit box is empty
            ToastMessage.showToastMessage(getActivity().getBaseContext(),"Message is empty..");

            status_text.setVisibility(View.VISIBLE);
            status_time.setVisibility(View.VISIBLE);
            status_edit_text.setVisibility(View.GONE);

        }
    }

    AsyncResultInterface asyncResult = new AsyncResultInterface() {
        @Override
        public void OnResultSuccess(String message) {
            try {
                JSONObject jsonObject = new JSONObject(message);
                String ok = jsonObject.getString("status");
                if (ok.equals("1"))
                {
                    // call to make currentstatus from 1 to 0
                    String server_id = jsonObject.getString("id");
                    dataBaseSQLite.changeCurrentStatus(dataBaseSQLite);
                    // insert into my_status table
                    // get time
                    String time = getDateTime();
                    dataBaseSQLite.insertIntoStatusTable(dataBaseSQLite, server_id, status_edit_text.getText().toString(), time);

                    status_text.setText("");
                    status_text.setText(status_edit_text.getText().toString());
                    status_time.setText(time);
                    getListView();
                    ToastMessage.showLogMessages("status inserted successfully...!!");
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


    public void goBackToEditButton()
    {
        //this function is used if the edit button is pressed and the back button is pressed....
        fab_edit.setImageResource(R.drawable.ic_edit);
        fab_edit_button_clicked = false;
        ((MainActivity)getActivity()).fabClickedFalse();

        status_text.setVisibility(View.VISIBLE);
        status_time.setVisibility(View.VISIBLE);
        status_edit_text.setVisibility(View.GONE);
    }



}
