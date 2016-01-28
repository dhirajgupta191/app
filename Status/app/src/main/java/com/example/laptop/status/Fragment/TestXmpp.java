package com.example.laptop.status.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.laptop.status.Connectivity.MyXMPP;
import com.example.laptop.status.R;

/**
 * Created by Laptop on 27-Jan-16.
 */
public class TestXmpp extends Fragment {

    EditText servername, hostname, port;
    Button send;

    public static TestXmpp getInstance(int position) {
        TestXmpp testXmpp = new TestXmpp();
        Bundle args = new Bundle();
        args.putInt("position", position);
        testXmpp.setArguments(args);
        return testXmpp;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test_xmpp, container, false);

        servername = (EditText) view.findViewById(R.id.serverName);
        hostname = (EditText) view.findViewById(R.id.hostName);
        port = (EditText) view.findViewById(R.id.portNo);

        send = (Button) view.findViewById(R.id.connect);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyXMPP myXMPP = new MyXMPP(getActivity(),servername.getText().toString(),hostname.getText().toString(),port.getText().toString());

            }
        });

        return view;
    }
}
