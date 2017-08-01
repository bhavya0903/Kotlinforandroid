package com.example.chandrayadav.xolo_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.HashMap;


public class LoginDataBaseAdapter {

    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text,PASSWORD text); ";
    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;


    public LoginDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String userName,String password)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", userName);
        newValues.put("PASSWORD",password);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
        Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int deleteEntry(String UserName)
    {
        //String id=String.valueOf(ID);
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    public String getSingleEntry(String userName)
    {
        Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }
//    public void  updateEntry(String userName,String password)
//    {
//        // Define the updated row content.
//        ContentValues updatedValues = new ContentValues();
//        // Assign values for each row.
//        updatedValues.put("USERNAME", userName);
//        updatedValues.put("PASSWORD",password);
//
//        String where="USERNAME = ?";
//        db.update("LOGIN",updatedValues, where, new String[]{userName});
//    }

    public String getAllTags(String a) {

        Cursor c = db.rawQuery("SELECT * FROM " + "LOGIN" + " where SECURITYHINT = '" +a + "'" , null);
        String str = null;
        if (c.moveToFirst()) {
            do {
                str = c.getString(c.getColumnIndex("PASSWORD"));
            } while (c.moveToNext());
        }
        return str;
    }

    public void updateEntry(String password,String repassword)
    {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("PASSWORD", password);
        updatedValues.put("REPASSWORD",repassword);
        updatedValues.put("SECURITYHINT",repassword);

        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{password});
    }

    public HashMap<String, String> getAnimalInfo(String id) {
        HashMap<String, String> wordList = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM LOGIN where SECURITYHINT='"+id+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                wordList.put("PASSWORD", cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return wordList;
    }
}

