package com.example.bookmanageapp.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.example.bookmanageapp.BookInfoActivity;
import com.example.bookmanageapp.BorrowBookInfoActivity;
import com.example.bookmanageapp.R;
import com.example.bookmanageapp.featureclass.BookItem;
import com.example.bookmanageapp.utils.ConstantValue;
import com.example.bookmanageapp.utils.UseLog;

import java.util.ArrayList;

public class MainViewPagerAdapter extends PagerAdapter {

    private Context mContext = null;

    private ArrayList<BookItem> mOwnBookList;
    private ArrayList<BookItem> mBorrowBookList;
    private ArrayList<BookItem> mTrackerBookList;

    public MainViewPagerAdapter(Context mContext, ArrayList<BookItem> ownList, ArrayList<BookItem> borrowLIst, ArrayList<BookItem> historyList) {
        this.mContext = mContext;
        mOwnBookList = ownList;
        mBorrowBookList = borrowLIst;
        mTrackerBookList = historyList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;

        if (mContext != null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            switch (position) {
                case 0:
                default:
                    view = inflater.inflate(R.layout.main_tab_own_booklist_layout, container, false);
                    ListView mOwnBookListListView = view.findViewById(R.id.listview_own_booklist);
                    OwnBookListAdapter ownBookListAdapter = new OwnBookListAdapter(mContext, mOwnBookList);
                    mOwnBookListListView.setAdapter(ownBookListAdapter);

                    Button tv = view.findViewById(R.id.tv_own_booklist_add_btn);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            UseLog.i("onClick add new book");
                            Intent intent = new Intent(mContext, BookInfoActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        }
                    });
                    mOwnBookListListView.setOnItemClickListener(ownBookListItemClickListener);
                    break;
                case 1:
                    view = inflater.inflate(R.layout.main_tab_borrow_booklist_layout, container, false);
                    ListView mBorrowBookListListView = view.findViewById(R.id.listview_borrow_booklist);
                    BorrowBookListAdapter borrowBookListAdapter = new BorrowBookListAdapter(mContext, mBorrowBookList);
                    mBorrowBookListListView.setAdapter(borrowBookListAdapter);
                    mBorrowBookListListView.setOnItemClickListener(borrowBookListItemClickListener);
                    break;
                case 2:
                    view = inflater.inflate(R.layout.main_tab_history_booklist_layout, container, false);
                    ListView mRHistoryListListView = view.findViewById(R.id.listview_history_booklist);
                    ReadingTrackerListAdapter rHistoryListAdapter = new ReadingTrackerListAdapter(mContext, mTrackerBookList);
                    mRHistoryListListView.setAdapter(rHistoryListAdapter);
                    break;
            }
        }

        container.addView(view);

        return view;
    }

    AdapterView.OnItemClickListener ownBookListItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(mContext, BookInfoActivity.class);
            intent.putExtra(ConstantValue.BOOK_ITEM_INTENT_DATA, mOwnBookList.get(i));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    };
    AdapterView.OnItemClickListener borrowBookListItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(mContext, BorrowBookInfoActivity.class);
            intent.putExtra(ConstantValue.BOOK_ITEM_INTENT_DATA, mBorrowBookList.get(i));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    };

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String returnString = "";
        switch (position) {
            case 0:
            default:
                returnString = mContext.getResources().getString(R.string.tab_menu_user_book_list);
                break;
            case 1:
                returnString = mContext.getResources().getString(R.string.tab_menu_borrow_book);
                break;
            case 2:
                returnString = mContext.getResources().getString(R.string.tab_menu_reading_history);
                break;
        }

        return returnString;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
