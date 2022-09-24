package com.example.bookmanageapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookmanageapp.R;
import com.example.bookmanageapp.featureclass.BookItem;
import com.example.bookmanageapp.featureclass.UserMessages;

import java.util.ArrayList;

public class UserMessageListAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<UserMessages> mDisplayData;


    public UserMessageListAdapter(Context context, ArrayList data) {
        super(context, 0, data);
        mContext = context;
        mDisplayData = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_item_layout, parent, false);
        }

        UserMessages um = mDisplayData.get(position);

        TextView msgText = convertView.findViewById(R.id.tv_message_text);
        msgText.setText(um.getSentMsgTxt());
        TextView msgSender = convertView.findViewById(R.id.tv_message_sender);
        msgSender.setText(um.getSenderID());

        return convertView;
    }
}
