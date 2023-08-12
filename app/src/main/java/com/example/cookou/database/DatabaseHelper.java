package com.example.cookou.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.cookou.CookouApp;
import com.example.cookou.models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class DatabaseHelper extends SQLiteOpenHelper {
    final static String DATABASE_NAME = "Cookou.db";
    final static String DDL = "Cookou-DDL.sql";
    final static int DATABASE_VERSION = 6;
    final AssetManager assetManager = CookouApp.getAppContext().getAssets(); //to get sql files from assets folder

    //User Columns
    private static final String USER_TABLE="Users";
    private static final String USER_COL1="user_id";
    private static final String USER_COL2="name";
    private static final String USER_COL3="email";
    private static final String USER_COL4="password";
    private static final String USER_COL5="is_active";

    //User_Diets columns
    private static final String USER_DIETS_TABLE="User_Diets";
    private static final String USER_DIETS_COL1="user_id";
    private static final String USER_DIETS_COL2="str_category";

    //User_Diets columns
    private static final String USER_AREA_TABLE="User_Areas";
    private static final String USER_AREA_COL1="user_id";
    private static final String USER_AREA_COL2="str_area";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        loadDDL(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        loadDDL(db);
    }

    private void loadDDL(SQLiteDatabase db){
        if(assetManager!=null){
            try{
                InputStream inputStream = assetManager.open(DDL);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine())!=null){
                    db.execSQL(line);
                }
                inputStream.close();
            }catch (IOException e){
                throw new RuntimeException(e);
            }

        }
    }

    //User related operations
    public void insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_COL2, user.getName());
        values.put(USER_COL3, user.getEmail());
        values.put(USER_COL4, user.getPassword());
        values.put(USER_COL5, user.isActive());
        db.insert(USER_TABLE, null, values);
    }
    public User getUser(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        User user = new User();
        Cursor cursor = db.rawQuery("Select * from Users where Email=? ", new String[] {email});

        if(cursor!=null){
            if(cursor.moveToFirst()){
                    user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(USER_COL1)));
                    user.setName(cursor.getString(cursor.getColumnIndexOrThrow(USER_COL2)));
                    user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(USER_COL3)));
                    user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(USER_COL4)));
                    user.setActive(cursor.getInt(cursor.getColumnIndexOrThrow(USER_COL4))==1);
            }
            cursor.close();
            return user;
        }


        return null;
    }
    public boolean authenticate (String email, String password){
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery("Select * from Users where Email=? and Password=?", new String[] {email,password});

        if (cursor.getCount() > 0 )
        {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean userExits(String email){
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery("Select * from Users where Email=? ", new String[] {email});

        if (cursor.getCount() > 0 )
        {
            cursor.close();
            return true;
        }

        cursor.close();
        return false;
    }
    public void updateActiveStatus(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if(user.isActive())
            values.put(USER_COL5,0);
        else
            values.put(USER_COL5,1);

        db.update(USER_TABLE, values, USER_COL1+"=?", new String[]{String.valueOf(user.getId())});
    }

    public void updateUserDiets(String category, int user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor cursor = db.rawQuery("Select * from User_Diets where str_category=? and user_id=? ", new String[] {category,String.valueOf(user_id)});

        if(cursor.getCount() > 0){
            Log.e("DB warnings","Category already favorited");
        }
        else{
            values.put(USER_DIETS_COL1,user_id);
            values.put(USER_DIETS_COL2,category);
            db.insert(USER_DIETS_TABLE, null, values);


        }
    }

    public void updateUserAreas(String area, int user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor cursor = db.rawQuery("Select * from User_Areas where str_area=? and user_id=? ", new String[] {area,String.valueOf(user_id)});

        if(cursor.getCount() > 0){
            Log.e("DB warnings","Area already favorited");
        }
        else{
            values.put(USER_AREA_COL1,user_id);
            values.put(USER_AREA_COL2,area);
            db.insert(USER_AREA_TABLE, null, values);

        }
    }

    public Set<String> getUserCategories(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Set<String> categories= new HashSet<>();

        Cursor cursor = null;
        db.beginTransaction();


        try{
            cursor = db.rawQuery("Select * from User_Diets where user_id=? ", new String[] {String.valueOf(id)});

            if(cursor!=null){
                if(cursor.moveToFirst()){
                    do{
                        String category = cursor.getString(cursor.getColumnIndexOrThrow(USER_DIETS_COL2));
                        categories.add(category);
                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return categories;
    }

    public Set<String> getUserAreas(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Set<String> areas= new HashSet<>();

        Cursor cursor = null;
        db.beginTransaction();


        try{
            cursor = db.rawQuery("Select * from User_Areas where user_id=? ", new String[] {String.valueOf(id)});

            if(cursor!=null){
                if(cursor.moveToFirst()){
                    do{
                        String area = cursor.getString(cursor.getColumnIndexOrThrow(USER_AREA_COL2));
                        areas.add(area);
                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return areas;
    }
}
