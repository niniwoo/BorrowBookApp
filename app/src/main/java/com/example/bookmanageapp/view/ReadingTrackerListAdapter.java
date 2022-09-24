package com.example.bookmanageapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookmanageapp.R;
import com.example.bookmanageapp.database.DBHelper;
import com.example.bookmanageapp.database.DBQuery;
import com.example.bookmanageapp.featureclass.BookItem;
import com.example.bookmanageapp.utils.UseLog;

import java.util.ArrayList;

public class ReadingTrackerListAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<BookItem> mDisplayData;

    private ImageView mIconImageView;
    private TextView mTitleTextView;
    private TextView mTextView1;
    private Switch mTgIsRead;

    private DBHelper mDBHelper;

    public ReadingTrackerListAdapter(Context context, ArrayList data) {
        super(context, 0, data);
        mContext = context;
        mDisplayData = data;
        mDBHelper = new DBHelper(mContext);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.readingtrackerlist_item_layout, parent, false);
        }
        BookItem book = mDisplayData.get(position);



        mTitleTextView = convertView.findViewById(R.id.tv_tracker_list_item_title);
        mTitleTextView.setText(book.getTitle());
        mTgIsRead = convertView.findViewById(R.id.tg_tracker_list_item_btn);
        mTgIsRead.setChecked(book.isRead());
        mTgIsRead.setTag(position);
        mTgIsRead.setOnClickListener(mTgClickListener);

        mIconImageView = convertView.findViewById(R.id.iv_tracker_list_item_layout);
        mTextView1 = convertView.findViewById(R.id.tv_tracker_list_item_layout_1);
        if (book.isRead()) {
            mTextView1.setText(mContext.getResources().getString(R.string.reading_tracker_reading_now));
            mIconImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.bookmark1));
        } else {
            mTextView1.setText(mContext.getResources().getString(R.string.reading_tracker_hold_the_reading));
            mIconImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.bookmark2));
        }
        return convertView;
    }

    View.OnClickListener mTgClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CompoundButton tgBtn = (CompoundButton) view;
            int position = (int) tgBtn.getTag();

            BookItem bookItem = mDisplayData.get(position);
            bookItem.setRead(tgBtn.isChecked());
            mDisplayData.set(position, bookItem);

            long result = DBQuery.updateBookInfoToBOOK(mDBHelper, bookItem);
            if (result <= 0) {
                Toast.makeText(mContext, mContext.getResources().getString(R.string.toast_failed_to_update_your_book_info), Toast.LENGTH_LONG).show();
            }

            if (bookItem.isRead()) {
                mTextView1.setText(mContext.getResources().getString(R.string.reading_tracker_reading_now));
                mIconImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.bookmark1));
            } else {
                mTextView1.setText(mContext.getResources().getString(R.string.reading_tracker_hold_the_reading));
                mIconImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.bookmark2));
            }
            notifyDataSetChanged();
        }
    };
}

