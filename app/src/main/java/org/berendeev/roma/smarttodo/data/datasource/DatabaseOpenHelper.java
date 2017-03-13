package org.berendeev.roma.smarttodo.data.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


public class DatabaseOpenHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String DATABASE_NAME = "smarttodo.db";
    private static final int DATABASE_VERSION = 4;

    public static final String NAME = "name";

    public static final String TODOS_TABLE = "todos";
    public static final String TODO_DESCRIPTION = "description";
    public static final String IS_CHECKED = "is_checked";
    public static final String CATEGORY_ID = "category_id";

    public static final String CATEGORIES_TABLE = "categories";
    public static final String IS_EXPANDED = "is_expanded";


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
        String script = "create table " + TODOS_TABLE + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                NAME + " text not null, " +
                TODO_DESCRIPTION + " text, " +
                IS_CHECKED + " integer not null, " +
                CATEGORY_ID + " integer);";
        db.execSQL(script);
        String categoriesScript = "create table " + CATEGORIES_TABLE + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                NAME + " text not null, " +
                IS_EXPANDED + " integer not null);";
        db.execSQL(categoriesScript);
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, "default");
        contentValues.put(IS_EXPANDED, 1);
        contentValues.put(_ID, 0);
        db.insert(CATEGORIES_TABLE, null, contentValues);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODOS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE);
        onCreate(db);
    }

    @Override public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TODOS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE);
        onCreate(db);
    }
}
