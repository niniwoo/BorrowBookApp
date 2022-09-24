package com.example.bookmanageapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookmanageapp.R;
import com.example.bookmanageapp.featureclass.BookItem;
import com.example.bookmanageapp.utils.ConstantValue;
import com.example.bookmanageapp.utils.UseLog;

import java.util.ArrayList;

public class BorrowBookListAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<BookItem> mDisplayData;

    private ImageView mIconImageView;
    private TextView mTitleTextView;
    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;

    public BorrowBookListAdapter(Context context, ArrayList data) {
        super(context, 0, data);
        mContext = context;
        mDisplayData = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.booklist_item_layout, parent, false);
        }

        BookItem book = mDisplayData.get(position);
        mIconImageView = convertView.findViewById(R.id.iv_list_item_layout);
        mIconImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.time));

        mTitleTextView = convertView.findViewById(R.id.tv_list_item_title);
        mTitleTextView.setText(book.getTitle());
        mTextView1 = convertView.findViewById(R.id.tv_list_item_layout_1);
        mTextView1.setText(book.getAuthor());
        mTextView2 = convertView.findViewById(R.id.tv_list_item_layout_2);
        mTextView2.setText(book.getPublisher());
        mTextView3 = convertView.findViewById(R.id.tv_list_item_layout_3);
        mTextView3.setText(String.format("%s %s", book.getOwnerID(), mContext.getResources().getString(R.string.book_list_own)));

        return convertView;
    }
}
