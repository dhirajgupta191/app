package com.example.laptop.status.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.laptop.status.Fragment.AddStatusFragment;
import com.example.laptop.status.MainActivity;
import com.example.laptop.status.R;
import com.example.laptop.status.ToastMessage;
import com.example.laptop.status.values.RobotoTextView;
import com.example.laptop.status.values.RoundedImageView;

/**
 * Created by Laptop on 06-Jan-16.
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.MyViewHolder> implements View.OnClickListener {

    LayoutInflater layoutInflater;
    String[] contactName;
    String[] contact_status;
    Context context = null;

    public ContactListAdapter(Context _context, String[] _contact_name, String[] _status) {
        context = _context;
        layoutInflater = LayoutInflater.from(context);
        contactName = _contact_name;
        contact_status = _status;
    }

    @Override
    public ContactListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflate_contact_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.contactName.setText(contactName[position]);
        holder.contactPhoneNo.setText(contact_status[position]);
        holder.contactMenuAdd.setOnClickListener(this);

    }


    @Override
    public int getItemCount() {
        return contactName.length;
    }

    @Override
    public void onClick(View v) {
        Context _context = new ContextThemeWrapper(context, R.style.PopupMenu);
        PopupMenu popup = new PopupMenu(_context, v);


        /*MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_contact_list, popup.getMenu());*/
        popup.inflate(R.menu.menu_contact_list);
//        popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) context);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_status:
                        AddStatusFragment addStatusFragment = new AddStatusFragment();

                        ((MainActivity) context).getSupportFragmentManager().beginTransaction().
                                add(R.id.fragment_container, addStatusFragment, AddStatusFragment.NAME)
                                .addToBackStack(AddStatusFragment.NAME)
                                .commit();
                        ToastMessage.showToastMessage(context, "ok.. dne");

                        return true;
                    default:
                        return false;
                }

            }
        });
        popup.show();
    }


    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_status:
                return true;
            default:
                return false;
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        RobotoTextView contactName, contactPhoneNo;
        ImageView contactMenuAdd;

        public MyViewHolder(View itemView) {
            super(itemView);
            contactName = (RobotoTextView) itemView.findViewById(R.id.contactName);
            contactPhoneNo = (RobotoTextView) itemView.findViewById(R.id.contactPhoneNo);
            contactMenuAdd = (ImageView) itemView.findViewById(R.id.contactMenuAdd);

        }
    }

}