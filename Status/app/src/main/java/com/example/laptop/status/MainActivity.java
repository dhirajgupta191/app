package com.example.laptop.status;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.laptop.status.Connectivity.DataBaseSQLite;
import com.example.laptop.status.Connectivity.SharePreference;
import com.example.laptop.status.Fragment.ContactFragment;
import com.example.laptop.status.Fragment.LoginFragment;
import com.example.laptop.status.Fragment.SlidingFragment;
import com.example.laptop.status.Fragment.StatusFragment;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    SlidingFragment slidingFragment;
    DataBaseSQLite dataBaseSQLite;
    SharePreference sharePreference;
    Context context;
    FragmentManager manager;
    FragmentTransaction fragmentTransaction;
    TextView userId;
    ViewPager viewPager;

    private boolean mBounded;
    public boolean fabClickedFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);


        context = getBaseContext();
        try {
            dataBaseSQLite = new DataBaseSQLite(getBaseContext());
//        Cursor cursor = dataBaseSQLite.checkLoginOrNot(dataBaseSQLite);

            sharePreference = new SharePreference();
            String userId = sharePreference.getValue(getBaseContext(), SharePreference.USER_ID);

            if (!userId.equals(SharePreference.DEFAULT_VALUE))   // check whether the user is login or not. if the cursor is null the user is not login.
            {
                /* toolbar setup */
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                toolbar.inflateMenu(R.menu.menu_main);
                toolbar.setTitle("Status");

                /**
                 *
                 */


                /* call the sliding Fragment Class... this class will call the sliding tab and fragment status and fragment contact.*/
                slidingFragment = new SlidingFragment();
                manager = getSupportFragmentManager();
                fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_container, slidingFragment, SlidingFragment.NAME);
                fragmentTransaction.commit();

            } else {
            /* tool bar setup*/
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                LoginFragment fragmentLoginActivity = new LoginFragment();
                manager = getSupportFragmentManager();
                fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_container, fragmentLoginActivity, "fragment_login_activity");
                fragmentTransaction.commit();


            }
        } catch (Exception error) {
            Log.e("error", error.toString());
        }
    }


    @Override
    public void onBackPressed() {
        if (fabClickedFlag) {
            SlidingFragment slidingFragment = (SlidingFragment) getSupportFragmentManager().findFragmentByTag(SlidingFragment.NAME);
            viewPager = slidingFragment.getViewPager();
            int pos = viewPager.getCurrentItem();
            if (pos == 0) {
                // if user is in status fragment and click the backbutton
                StatusFragment statusFragment = (StatusFragment) viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());
                statusFragment.goBackToEditButton();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }

    }

    public void fabClickedTrue() {
        // calling from the fragment to check fab clicked true
        fabClickedFlag = true;
    }

    public void fabClickedFalse() {
        // calling from the fragment to check fab clicked false;
        fabClickedFlag = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profile_settings) {
            try {
                Intent intent = new Intent(context, ProfileSettings.class);
                startActivity(intent);

            } catch (ActivityNotFoundException error) {
                ToastMessage.showLogMessages(error.toString());
            }
            return true;
        }
        if (id == R.id.group_settings) {
            ToastMessage.showToastMessage(context, "group settings");
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            return rootView;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}

