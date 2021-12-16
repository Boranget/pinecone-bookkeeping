package com.blackorangejuice.songguojizhang.db.mapper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blackorangejuice.songguojizhang.bean.AccountBook;
import com.blackorangejuice.songguojizhang.db.SongGuoDatabaseHelper;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class AccountBookMapper {
    public static final String INSERT_ACCOUNT_BOOK = "insert into t_account_book \n" +
            "(account_book_name)\n" +
            "values\n" +
            "(?)";

    public static final String UPDATE_ACCOUNT_BOOK_NAME = "update t_account_book\n" +
            "set account_book_name = ?\n" +
            "where bid = ?";

    public static final String DELETE_ACCOUNT_BOOK = "delete from t_account_book\n" +
            "    where bid = ?";

    public static final String SELETE_BY_BID = "select * from t_account_book where bid = ?";

    public static final String SELETE_BY_ACCOUNT_BOOK_NAME = "select * from t_account_book where account_book_name = ?";
    public static final String SELETE_ALL = "select * from t_account_book";


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
        sqLiteDatabase.execSQL(INSERT_ACCOUNT_BOOK,new String[]{accountBook.getAccountBookName()});
        return selectByAccountBookName(accountBook.getAccountBookName());
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
     * @param accountBook
     */
    public void deleteAccountBook(AccountBook accountBook){

        sqLiteDatabase.execSQL(DELETE_ACCOUNT_BOOK,new String[]{String.valueOf(accountBook.getBid())});
    }


    /**
     * 通过uid和AccountBookName获取账本
     * @param accountBookName
     * @return
     */
    public AccountBook selectByAccountBookName(String accountBookName){
        Cursor cursor = sqLiteDatabase.rawQuery(SELETE_BY_ACCOUNT_BOOK_NAME,new String[]{accountBookName});
        AccountBook accountBook = new AccountBook();
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
            String accountBookName = cursor.getString(cursor.getColumnIndex("account_book_name"));
            accountBook.setAccountBookName(accountBookName);
        }
        cursor.close();
        return accountBook;
    }
    public List<AccountBook> selectAll(){
        List<AccountBook> accountBooks = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(SELETE_ALL,null);

        if(cursor.moveToFirst()){
            do{
                AccountBook accountBook = new AccountBook();
                Integer bid = cursor.getInt(cursor.getColumnIndex("bid"));
                String accountBookName = cursor.getString(cursor.getColumnIndex("account_book_name"));
                accountBook.setAccountBookName(accountBookName);
                accountBook.setBid(bid);
                accountBooks.add(accountBook);
            }while (cursor.moveToNext());

        }
        cursor.close();
        return accountBooks;
    }
}
