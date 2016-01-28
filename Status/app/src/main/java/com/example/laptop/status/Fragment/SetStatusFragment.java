package com.example.laptop.status.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.laptop.status.Adapter.SetStatusAdapter;
import com.example.laptop.status.MainActivity;
import com.example.laptop.status.R;

/**
 * Created by Laptop on 06-Dec-15.
 */
public class SetStatusFragment extends Fragment {

    View view;
    ListView setStatusListView;

    public static SetStatusFragment getInstance(int position) {
        SetStatusFragment fragmentStatus = new SetStatusFragment();
        Bundle args = new Bundle();
        args.putInt("position",position);
        fragmentStatus.setArguments(args);
        return fragmentStatus;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_set_status,null);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // get the listview id
        setStatusListView = (ListView) view.findViewById(R.id.setStatusListView);

        String[] contactName = {"Tombrady","Tombrady"};
        String[] statusText = {"status: i will be there in 5 mins..","status: i will be there in 15 mins i will be there in 15 mins"};
        int[] contactProfile = {R.drawable.chicken,R.drawable.chicken};

        setStatusListView.setAdapter(new SetStatusAdapter(getActivity().getBaseContext(),contactName,statusText,contactProfile));

    }

}
