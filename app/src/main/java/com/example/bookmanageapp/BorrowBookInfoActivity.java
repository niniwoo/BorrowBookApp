package com.example.bookmanageapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookmanageapp.database.DBHelper;
import com.example.bookmanageapp.database.DBQuery;
import com.example.bookmanageapp.featureclass.BookItem;
import com.example.bookmanageapp.featureclass.ReadingHistory;
import com.example.bookmanageapp.featureclass.UserMessages;
import com.example.bookmanageapp.utils.ConstantValue;
import com.example.bookmanageapp.utils.UseLog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BorrowBookInfoActivity extends BasementActivity {

    private EditText mTitleEdit;
    private EditText mAuthorEdit;
    private EditText mPublisherEdit;
    private EditText mYearEdit;
    private EditText mRentFeeEdit;
    private EditText mOwnerIDEdit;
    private Button mSubmitBtn;

    private BookItem mBook;
    private int mBookID;
    private boolean mIsGiveActivity = false;

    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UseLog.i("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        if (!getUserAccount().isLogin(getApplicationContext())) {
            UseLog.i("logout status");
            // send it to login activity
        }

        mTitleEdit = findViewById(R.id.edit_book_info_title);
        mAuthorEdit = findViewById(R.id.edit_book_info_author);
        mPublisherEdit = findViewById(R.id.edit_book_info_publisher);
        mYearEdit = findViewById(R.id.edit_book_info_year);
        mRentFeeEdit = findViewById(R.id.edit_book_info_rent_fee);
        mOwnerIDEdit = findViewById(R.id.edit_book_info_owner_id);
        mSubmitBtn = findViewById(R.id.btn_book_info_submit);

        mTitleEdit.setEnabled(false);
        mAuthorEdit.setEnabled(false);
        mPublisherEdit.setEnabled(false);
        mYearEdit.setEnabled(false);
        mRentFeeEdit.setEnabled(false);
        mOwnerIDEdit.setEnabled(false);

        // enabled elements
        findViewById(R.id.tv_book_info_owner_id).setVisibility(View.VISIBLE);
        findViewById(R.id.edit_book_info_owner_id).setVisibility(View.VISIBLE);

        // disabled unnecessary elements
        findViewById(R.id.tv_book_info_book_status).setVisibility(View.GONE);
        findViewById(R.id.rg_book_info_book_status).setVisibility(View.GONE);

        mDBHelper = new DBHelper(getApplicationContext());
        mSubmitBtn.setOnClickListener(SubmitBtnClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UseLog.i("onResume");
        if (getIntent() != null && getIntent().getParcelableExtra(ConstantValue.BOOK_ITEM_INTENT_DATA) != null) {
            mBook = getIntent().getParcelableExtra(ConstantValue.BOOK_ITEM_INTENT_DATA);
        } else {
            UseLog.i("onResume book data is wrong");
            finish();
        }

        mBookID = mBook.getBookID();

        mTitleEdit.setText(mBook.getTitle());
        mAuthorEdit.setText(mBook.getAuthor());
        mPublisherEdit.setText(mBook.getPublisher());
        mYearEdit.setText(mBook.getPublishYear());
        mOwnerIDEdit.setText(mBook.getOwnerID());
        mRentFeeEdit.setText(String.valueOf(mBook.getRentFee()));

        switch (mBook.getStatus()) {
            case ConstantValue.BOOK_STATUS_SHARE:
                mSubmitBtn.setText(getApplicationContext().getResources().getString(R.string.book_info_share_book));
                mIsGiveActivity = false;
                break;
            case ConstantValue.BOOK_STATUS_GIVEAWAY:
                mSubmitBtn.setText(getApplicationContext().getResources().getString(R.string.book_info_take_book));
                mIsGiveActivity = true;
                break;
            case ConstantValue.BOOK_STATUS_RENT:
                mSubmitBtn.setText(getApplicationContext().getResources().getString(R.string.book_info_rent_book));
                mIsGiveActivity = false;
                break;
        }

        mSubmitBtn.requestFocus();
    }


    View.OnClickListener SubmitBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BookItem bookItem = mBook;
            if (mIsGiveActivity) {
                // giveaway book process
                // int bookID, String title, String author, String publisher, String publishYear, String ownerID,
                // String renterID, String status, float rentFee, boolean isRead
                // change ownerID, renterID
                String oldOwnerID = mBook.getOwnerID();
                bookItem.setOwnerID(getUserAccount().getId());
                bookItem.setRenterID(getUserAccount().getId());
                bookItem.setRead(false);
                long result = DBQuery.updateBookInfoToBOOK(mDBHelper, bookItem);
                if (result > 0) {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.toast_succeed_to_take_this_book), Toast.LENGTH_LONG).show();
                    // String userID, String senderID, String sentMsgTxt
                    UserMessages um = new UserMessages(oldOwnerID, bookItem.getRenterID(),
                            getResources().getString(R.string.user_msg_your_book_is_given) + " " + mBook.getRenterID());
                    DBQuery.insertMessagesToMSG(mDBHelper, um);

                    //make new Reading history
                    Date date = new Date( System.currentTimeMillis());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String getTime = sdf.format(date);
                    ReadingHistory rh = new ReadingHistory(
                            getUserAccount().getId(), bookItem.getBookID(), bookItem.getTitle(), getTime);
                    DBQuery.insertHistoryToRHISTORY(mDBHelper, rh);
                    finish();
                }
            } else {
                // borrow book process
                // int bookID, String title, String author, String publisher, String publishYear, String ownerID,
                // String renterID, String status, float rentFee, boolean isRead
                // change renterID
                bookItem.setRenterID(getUserAccount().getId());
                bookItem.setRead(false);
                long result = DBQuery.updateBookInfoToBOOK(mDBHelper, bookItem);
                if (result > 0) {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.toast_succeed_to_borrow_this_book), Toast.LENGTH_LONG).show();
                    // String userID, String senderID, String sentMsgTxt
                    UserMessages um = new UserMessages(mBook.getOwnerID(), bookItem.getRenterID(),
                            getResources().getString(R.string.user_msg_your_book_is_borrowed) + " " + mBook.getRenterID());
                    DBQuery.insertMessagesToMSG(mDBHelper, um);

                    //make new Reading history
                    Date date = new Date( System.currentTimeMillis());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String getTime = sdf.format(date);
                    ReadingHistory rh = new ReadingHistory(
                            getUserAccount().getId(), bookItem.getBookID(), bookItem.getTitle(), getTime);
                    DBQuery.insertHistoryToRHISTORY(mDBHelper, rh);
                    finish();
                }
            }
        }
    };
}