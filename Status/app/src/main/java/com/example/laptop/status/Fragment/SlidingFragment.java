package com.example.laptop.status.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laptop.status.R;
import com.example.laptop.status.SlidingTabLayout;

/**
 * Created by Laptop on 05-Oct-15.
 */
public class SlidingFragment extends Fragment {
    ViewPager viewPager;
    SlidingTabLayout mTabs;
    MyPager myPager;
    View view;
    public static final String NAME = "sliding_fragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate is a function that takes the xml file & convert it into java object.
        view =  inflater.inflate(R.layout.sliding_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
          /* view pager */
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        myPager = new MyPager(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(myPager);

        mTabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setViewPager(viewPager);
    }

    public ViewPager getViewPager()
    {
        return viewPager;
    }

    class MyPager extends FragmentPagerAdapter {

        String[] tabs = {"STATUS","SET STATUS", "CONTACTS"};
        public MyPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if(position == 0) {
                StatusFragment statusFragment =   StatusFragment.getInstance(position,StatusFragment.NAME);
                fragment = statusFragment;
            }
            if(position == 1) {
                fragment = SetStatusFragment.getInstance(position);

            }
            if(position == 2)
            {
//                fragment = ContactFragment.getInstance(position);
                fragment = TestXmpp.getInstance(position);

            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position){
            return tabs[position];
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
