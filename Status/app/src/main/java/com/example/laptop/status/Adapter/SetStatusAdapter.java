package com.example.laptop.status.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.laptop.status.R;
import com.example.laptop.status.values.RobotoTextView;
import com.example.laptop.status.values.RoundedImageView;

/**
 * Created by Laptop on 06-Dec-15.
 */
public class SetStatusAdapter extends BaseAdapter {
    String[] contactName;
    String[] status;
    int[] profileimage;
    Context context;

    public SetStatusAdapter(Context context, String[] contactNametextView, String[] statusText, int[] profileimageView)
    {
        this.context = context;
        this.contactName = contactNametextView;
        this.status = statusText;
        this.profileimage = profileimageView;
    }
    @Override
    public int getCount() {
        return contactName.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
        {
            view = layoutInflater.inflate(R.layout.inflate_set_status,null);
            RobotoTextView contactText = (RobotoTextView) view.findViewById(R.id.contactName);
            RobotoTextView statusText = (RobotoTextView) view.findViewById(R.id.statusText);
            RoundedImageView imageView = (RoundedImageView) view.findViewById(R.id.profilePic);

            contactText.setText(contactName[position]);
            statusText.setText(status[position]);
            imageView.setImageResource(profileimage[position]);

        }
        else
        {
            view = convertView;
        }
        return view;
    }
}
