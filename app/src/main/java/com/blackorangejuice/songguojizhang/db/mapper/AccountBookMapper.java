package com.blackorangejuice.songguojizhang.db.mapper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blackorangejuice.songguojizhang.bean.AccountBook;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class AccountBookMapper {
    public static final String INSERT_ACCOUNT_BOOK = "insert into t_account_book\n" +
            "            (uid,account_book_name)\n" +
            "    values\n" +
            "            (?,?)";
    public static final String UPDATE_ACCOUNT_BOOK_NAME = "update t_account_book\n" +
            "    set account_book_name = ?\n" +
            "    where bid = ?";
    public static final String DELETE_ACCOUNT = "delete from t_account_book\n" +
            "    where bid = ?";
    public static final String SELECT_BY_UID = "select * from t_account_book where uid = ?";
    public static final String SELETE_BY_BID = "select * from t_account_book where bid = ?";
    public static final String SELETE_BY_UID_AND_ACCOUNT_BOOK_NAME = "select * from t_account_book where uid = ? and account_book_name = ?";


    SongGuoDatabaseHelper songGuoDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;
    /**
     * 构造方法传入helper
     * @param songGuoDatabaseHelper
     */
    public AccountBookMapper(SongGuoDatabaseHelper songGuoDatabaseHelper){
        this.songGuoDatabaseHelper = songGuoDatabaseHelper;
        sqLiteDatabase = songGuoDatabaseHelper.getWritableDatabase();
    }

    /**
     * 插入账本
     * @param accountBook
     * @return
     */
    public AccountBook insertAccountBook(AccountBook accountBook){
        sqLiteDatabase.execSQL(INSERT_ACCOUNT_BOOK,new String[]{String.valueOf(accountBook.getUid()),accountBook.getAccountBookName()});
        return selectByUidAndAccountBookName(accountBook.getUid(),accountBook.getAccountBookName());
    }

    /**
     * 更新账本名
     * @param accountBook
     * @return
     */
    public AccountBook updateAccountBookName(AccountBook accountBook){
        sqLiteDatabase.execSQL(UPDATE_ACCOUNT_BOOK_NAME,new String[]{accountBook.getAccountBookName(), String.valueOf(accountBook.getBid())});
        return selectByBid(accountBook.getBid());
    }

    /**
     * 删除帐本
     * @param bid
     */
    public void deleteAccountBook(Integer bid){
        sqLiteDatabase.execSQL(DELETE_ACCOUNT,new String[]{String.valueOf(bid)});
    }

    /**
     * 通过uid查找账本列表
     * @param uid
     * @return
     */
    public List<AccountBook> selectListByUid(Integer uid){
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_BY_UID,new String[]{String.valueOf(uid)});
        List<AccountBook> accountBooks = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                AccountBook accountBook = new AccountBook();
                accountBook.setUid(uid);
                Integer bid = cursor.getInt(cursor.getColumnIndex("bid"));
                String accountBookName = cursor.getString(cursor.getColumnIndex("account_book_name"));
                accountBook.setBid(bid);
                accountBook.setAccountBookName(accountBookName);
                accountBooks.add(accountBook);
            }while (cursor.moveToNext());

        }
        cursor.close();
        return accountBooks;
    }

    /**
     * 通过uid和AccountBookName获取账本
     * @param uid
     * @param accountBookName
     * @return
     */
    public AccountBook selectByUidAndAccountBookName(Integer uid, String accountBookName){
        Cursor cursor = sqLiteDatabase.rawQuery(SELETE_BY_UID_AND_ACCOUNT_BOOK_NAME,new String[]{String.valueOf(uid),accountBookName});
        AccountBook accountBook = new AccountBook();
        accountBook.setUid(uid);
        accountBook.setAccountBookName(accountBookName);
        if(cursor.moveToFirst()){
            Integer bid = cursor.getInt(cursor.getColumnIndex("bid"));
            accountBook.setBid(bid);
        }
        cursor.close();
        return accountBook;
    }

    /**
     * 通过bid查账本
     * @param bid
     * @return
     */
    public AccountBook selectByBid(Integer bid){
        Cursor cursor = sqLiteDatabase.rawQuery(SELETE_BY_BID,new String[]{String.valueOf(bid)});
        AccountBook accountBook = new AccountBook();
        accountBook.setBid(bid);

        if(cursor.moveToFirst()){
            Integer uid = cursor.getInt(cursor.getColumnIndex("uid"));
            String accountBookName = cursor.getString(cursor.getColumnIndex("account_book_name"));
            accountBook.setUid(uid);
            accountBook.setAccountBookName(accountBookName);
        }
        cursor.close();
        return accountBook;
    }
}
