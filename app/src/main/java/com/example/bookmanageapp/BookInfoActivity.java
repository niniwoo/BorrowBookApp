package com.example.bookmanageapp;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bookmanageapp.database.DBHelper;
import com.example.bookmanageapp.database.DBQuery;
import com.example.bookmanageapp.featureclass.BookItem;
import com.example.bookmanageapp.featureclass.ReadingHistory;
import com.example.bookmanageapp.utils.ConstantValue;
import com.example.bookmanageapp.utils.UseLog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BookInfoActivity extends BasementActivity {

    private EditText mTitleEdit;
    private EditText mAuthorEdit;
    private EditText mPublisherEdit;
    private EditText mYearEdit;
    private RadioGroup mBookStatusRG;
    private RadioButton mShareRB;
    private RadioButton mGiveawayRB;
    private RadioButton mRentRB;
    private EditText mRentFeeEdit;
    private Button mSubmitBtn;

    private BookItem mBook;
    private int mBookID;

    private DBHelper mDBHelper;

    private boolean mIsAddBookActivity = true;


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
        mBookStatusRG = findViewById(R.id.rg_book_info_book_status);
        mShareRB = findViewById(R.id.rb_share);
        mGiveawayRB = findViewById(R.id.rb_giveaway);
        mRentRB = findViewById(R.id.rb_rent);

        mSubmitBtn = findViewById(R.id.btn_book_info_submit);

        mDBHelper = new DBHelper(getApplicationContext());
        mSubmitBtn.setOnClickListener(SubmitClickListener);
        mBookStatusRG.setOnCheckedChangeListener(BookStatusChangeListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UseLog.i("onResume");
        if (getIntent() != null && getIntent().getParcelableExtra(ConstantValue.BOOK_ITEM_INTENT_DATA) != null) {
            mBook = getIntent().getParcelableExtra(ConstantValue.BOOK_ITEM_INTENT_DATA);
            mIsAddBookActivity = false;

            // update book data to activity
            mBookID = mBook.getBookID();
            mTitleEdit.setText(mBook.getTitle());
            mAuthorEdit.setText(mBook.getAuthor());
            mPublisherEdit.setText(mBook.getPublisher());
            mYearEdit.setText(mBook.getPublishYear());
            switch (mBook.getStatus()) {
                case ConstantValue.BOOK_STATUS_SHARE:
                    mShareRB.setChecked(true);
                    break;
                case ConstantValue.BOOK_STATUS_GIVEAWAY:
                    mGiveawayRB.setChecked(true);
                    break;
                case ConstantValue.BOOK_STATUS_RENT:
                    mRentRB.setChecked(true);
                    break;
            }

            mRentFeeEdit.setText(String.valueOf(mBook.getRentFee()));
            mSubmitBtn.setText(getApplicationContext().getResources().getString(R.string.book_info_update_book));
        } else {
            mIsAddBookActivity = true;
            mSubmitBtn.setText(getApplicationContext().getResources().getString(R.string.book_info_add_book));
            mShareRB.setChecked(true);
        }
    }

    View.OnClickListener SubmitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String newTitle = mTitleEdit.getText().toString();
            String newAuthor = mAuthorEdit.getText().toString();
            String newPublisher = mPublisherEdit.getText().toString();
            String newYear = mYearEdit.getText().toString();
            String newRentFee = "";
            String newStatus = "";

            switch (mBookStatusRG.getCheckedRadioButtonId()) {
                case R.id.rb_share:
                    newStatus = ConstantValue.BOOK_STATUS_SHARE;
                    newRentFee = "0";
                    break;
                case R.id.rb_giveaway:
                    newStatus = ConstantValue.BOOK_STATUS_GIVEAWAY;
                    newRentFee = "0";
                    break;
                case R.id.rb_rent:
                    newStatus = ConstantValue.BOOK_STATUS_RENT;
                    newRentFee = mRentFeeEdit.getText().toString();
                    break;
            }

            if (newTitle.equals("") || newAuthor.equals("") || newPublisher.equals("")
                    || newYear.equals("") || newRentFee.equals("") || newStatus.equals("")) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_input_data_is_wrong), Toast.LENGTH_LONG).show();
                return;
            }

            if (mIsAddBookActivity) {
                // add new book proces
                // int bookID, String title, String author, String publisher, String publishYear, String ownerID,
                // String renterID, String status, float rentFee, boolean isRead
                // bookID = 0, isRead = false
                // ownerID = renterID = userID
                BookItem bookItem = new BookItem(0, newTitle, newAuthor, newPublisher, newYear, getUserAccount().getId(),
                        getUserAccount().getId(), newStatus, Float.parseFloat(newRentFee), false);
                long result = DBQuery.insertBookInfoToBOOK(mDBHelper, bookItem);
                if (result > 0) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_succeed_to_add_new_book), Toast.LENGTH_LONG).show();


                    //make new Reading history
                    Date date = new Date( System.currentTimeMillis());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String getTime = sdf.format(date);
                    ReadingHistory rh = new ReadingHistory(
                            getUserAccount().getId(), (int) result, bookItem.getTitle(), getTime);
                    DBQuery.insertHistoryToRHISTORY(mDBHelper, rh);

                    finish();
                }
            } else {
                // update book process
                // int bookID, String title, String author, String publisher, String publishYear, String ownerID,
                // String renterID, String status, float rentFee, boolean isRead
                // keep bookID, ownerID, renterID, isRead
                BookItem bookItem = new BookItem(mBook.getBookID(), newTitle, newAuthor, newPublisher, newYear, mBook.getOwnerID(),
                        mBook.getRenterID(), newStatus, Float.parseFloat(newRentFee), mBook.isRead());
                long result = DBQuery.updateBookInfoToBOOK(mDBHelper, bookItem);
                if (result > 0) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_succeed_to_update_your_book_info), Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    };

    RadioGroup.OnCheckedChangeListener BookStatusChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (i == R.id.rb_rent) {
                mRentFeeEdit.setEnabled(true);
            } else {
                mRentFeeEdit.setText("");
                mRentFeeEdit.setEnabled(false);
            }
        }
    };
}