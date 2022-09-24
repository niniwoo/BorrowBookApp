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
import com.example.bookmanageapp.utils.ConstantValue;

import java.util.ArrayList;


public class OwnBookListAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<BookItem> mDisplayData;

    private ImageView mIconImageView;
    private TextView mTitleTextView;
    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;

    public OwnBookListAdapter(Context context, ArrayList data) {
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
        mIconImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.book));

        mTitleTextView = convertView.findViewById(R.id.tv_list_item_title);
        mTitleTextView.setText(book.getTitle());
        mTextView1 = convertView.findViewById(R.id.tv_list_item_layout_1);
        mTextView1.setText(book.getAuthor());
        mTextView2 = convertView.findViewById(R.id.tv_list_item_layout_2);
        mTextView2.setText(book.getPublisher());
        mTextView3 = convertView.findViewById(R.id.tv_list_item_layout_3);
        String txtBuild;
        if (book.getOwnerID().equals(book.getRenterID())) {
            // owned now
            txtBuild = mContext.getResources().getString(R.string.book_list_own);
        } else {
            // shared or rented
            if (book.getStatus().equals(ConstantValue.BOOK_STATUS_RENT)) {
                txtBuild = String.format("%s %s", mContext.getResources().getString(R.string.book_list_rent), book.getRenterID());
            } else {
                txtBuild = String.format("%s %s", mContext.getResources().getString(R.string.book_list_share), book.getRenterID());
            }
        }

        mTextView3.setText(txtBuild);

        return convertView;
    }
}
