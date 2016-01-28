package com.example.laptop.status.Fragment;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laptop.status.AsyncResultInterface;
import com.example.laptop.status.Connectivity.DataBaseSQLite;
import com.example.laptop.status.Connectivity.DataBaseServer;
import com.example.laptop.status.Connectivity.SharePreference;
import com.example.laptop.status.R;
import com.example.laptop.status.ToastMessage;

import org.json.JSONObject;

/**
 * Created by Laptop on 29-Sep-15.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    Button Next;
    EditText UserName,PhoneNo;
    TextView countryCodeTextBox;
    View view;
    DataBaseSQLite dataBaseSQLite;
    SharePreference sharePreference;

    DataBaseServer dataBaseServer;
    ConnectivityManager connectivityManager;
    FragmentTransaction fragmentTransaction;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login_activity, container, false);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Next = (Button) view.findViewById(R.id.next);
        UserName = (EditText) view.findViewById(R.id.username);
        PhoneNo = (EditText) view.findViewById(R.id.phone_no);
        countryCodeTextBox = (TextView) view.findViewById(R.id.country_code_text_box);

        Next.setOnClickListener(this);
    }

    AsyncResultInterface asyncResult = new AsyncResultInterface() {
        @Override
        public void OnResultSuccess(String message) {
            try {

                JSONObject jsonObject = new JSONObject(message);
                String status = jsonObject.getString("status");
                if(status.equals("1")) {
                    String msg = jsonObject.getString("msg");
                    sharePreference = new SharePreference();
                    sharePreference.setValue(getActivity().getBaseContext(), SharePreference.USER_ID, msg);
                    sharePreference.setValue(getActivity().getBaseContext(),SharePreference.COUNTRY_CODE,"91");
                    SlidingFragment slidingFragment = new SlidingFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,slidingFragment,SlidingFragment.NAME)
                            .commit();
                }
                else
                {

                }
            }
            catch (Exception e)
            {
                ToastMessage.showLogMessages(e.toString());
            }
        }

        @Override
        public void OnResultSuccess(int flag, String server_id) {
            // status update..
        }

        @Override
        public void OnResultSuccess() {
            // profile pic upload
        }

        @Override
        public void OnResultFail() {
            ToastMessage.showLogMessages("not successful");
        }
    };


    @Override
    public void onClick(View v) {

        String username = UserName.getText().toString();
        String phoneno = PhoneNo.getText().toString();
        String countryCode = countryCodeTextBox.getText().toString();

                /*if the user gives the username, phone_no then call the doInBackGround 'register' and
                * if the user gets the sms then call the doInBackGround 'login'*/
        String method = "register";
        try {
                /*check whether the user have internet connection or not*/
            connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                /*call the database server to save the username, phone no...*/
                dataBaseServer = new DataBaseServer(getActivity());
                dataBaseServer.SetOnAsyncResult(asyncResult);
                dataBaseServer.execute(method, username, countryCode + phoneno);
                    /* check whether the databaseSQLite is update or not..
                    * this is the only process i m getting to check whether registration is successful or not..*/


            } else {
                ToastMessage.showToastMessage(getActivity(), "Check the internet connection.");
            }

        } catch (Exception e) {
            Log.e("error", "FragmentLoginActivity error >" + e.toString());
        }
    }
}


