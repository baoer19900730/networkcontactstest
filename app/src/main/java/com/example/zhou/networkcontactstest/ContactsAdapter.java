package com.example.zhou.networkcontactstest;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.List;

/**
 * Created by zhou on 2017/6/25.
 */

public class ContactsAdapter extends ArrayAdapter<Contacts> {

    private int resourceId;

   public ContactsAdapter(Context context, int textViewResourceId, List<Contacts> objects){
       super(context, textViewResourceId, objects);
       resourceId = textViewResourceId;
   }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contacts contacts = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.contacts_image);
        TextView user = (TextView) view.findViewById(R.id.user);
        TextView telephone = (TextView) view.findViewById(R.id.telephone);
        user.setText(contacts.getName());
        telephone.setText(contacts.getTelephone());
        imageView.setImageBitmap(contacts.getimageUrl());
        return view;
    }
}
