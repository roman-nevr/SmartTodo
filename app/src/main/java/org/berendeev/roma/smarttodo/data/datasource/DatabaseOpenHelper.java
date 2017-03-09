package org.berendeev.roma.smarttodo.data.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


public class DatabaseOpenHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String DATABASE_NAME = "smarttodo.db";
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_TABLE = "todos";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String CATEGORY_ID = "category_id";

//    private static DatabaseOpenHelper instance;
//
//    public static DatabaseOpenHelper getInstance (Context context){
//        if (instance == null){
//            return new DatabaseOpenHelper(context);
//        } else {
//            return instance;
//        }
//    }

    public DatabaseOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        String script = "create table " + DATABASE_TABLE + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                NAME + " text not null, " +
                DESCRIPTION + " text, " +
                CATEGORY_ID + " real);";
        db.execSQL(script);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
}
