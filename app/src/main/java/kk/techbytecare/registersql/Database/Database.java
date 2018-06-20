package kk.techbytecare.registersql.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import kk.techbytecare.registersql.Model.User;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "RegisterSQL.db";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null,DB_VER);
    }

    public boolean registerUser(String name, String phone, String email, String password)  {
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("INSERT OR REPLACE INTO Users(Name,Phone,Email,Password) VALUES('%s','%s','%s','%s');",
                name, phone, email, password);
        database.execSQL(query);
        return true;
    }

    public boolean loginUser(String phone, String password) {
        boolean check;

        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("SELECT * From Users WHERE Phone = '%s' AND Password = '%s'",phone,password);
        Cursor cursor = database.rawQuery(query,null);
        if (cursor.getCount()  > 0) {
            check = true;
        }
        else {
            check = false;
        }
        cursor.close();
        return check;
    }

    public boolean checkUserExists(String phone)  {
        boolean check;

        SQLiteDatabase database = getReadableDatabase();

        String query = String.format("SELECT * From Users WHERE Phone = '%s'",phone);
        Cursor cursor = database.rawQuery(query,null);

        if (cursor.getCount()  > 0) {
            check = true;
        }
        else {
            check = false;
        }
        cursor.close();
        return check;
    }

    public List<User> getAllUsers(String phone)   {

        SQLiteDatabase database = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Id","Name","Phone","Email","Password"};

        String sqlTable = "Users";

        queryBuilder.setTables(sqlTable);

        Cursor cursor = queryBuilder.query(database,sqlSelect,"Phone=?",new String[]{phone},null,null,null);

        final List<User> result = new ArrayList<>();

        if (cursor.moveToFirst())    {
            do {
                result.add(new User(
                        cursor.getString(cursor.getColumnIndex("Name")),
                        cursor.getString(cursor.getColumnIndex("Phone")),
                        cursor.getString(cursor.getColumnIndex("Email")),
                        cursor.getString(cursor.getColumnIndex("Password"))));
            }
            while (cursor.moveToNext());
        }
        return result;
    }
}
