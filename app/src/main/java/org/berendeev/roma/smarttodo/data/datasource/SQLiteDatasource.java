package org.berendeev.roma.smarttodo.data.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDiskIOException;

import org.berendeev.roma.smarttodo.domain.model.Product;
import org.berendeev.roma.smarttodo.domain.model.PurchasingList;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import timber.log.Timber;

import static android.provider.BaseColumns._ID;
import static org.berendeev.roma.smarttodo.data.datasource.DatabaseOpenHelper.CATEGORY_ID;
import static org.berendeev.roma.smarttodo.data.datasource.DatabaseOpenHelper.DATABASE_TABLE;
import static org.berendeev.roma.smarttodo.data.datasource.DatabaseOpenHelper.DESCRIPTION;
import static org.berendeev.roma.smarttodo.data.datasource.DatabaseOpenHelper.NAME;

public class SQLiteDatasource implements Datasource {

    private final DatabaseOpenHelper openHelper;
    private final ContentValues contentValues;
    private SQLiteDatabase database;

    public SQLiteDatasource(DatabaseOpenHelper openHelper) {
        this.openHelper = openHelper;
        database = openHelper.getWritableDatabase();
        contentValues = new ContentValues();
    }

    @Override public Observable<Void> saveToDo(ToDo toDo) {
        contentValues.clear();
//        contentValues.put(_ID, toDo.id());
        contentValues.put(NAME, toDo.name());
        contentValues.put(DESCRIPTION, toDo.description());
        contentValues.put(CATEGORY_ID, toDo.categoryId());
        long rowId = database.insert(DATABASE_TABLE, null, contentValues);
        if(rowId == -1){
            return Observable.error(new SQLException("can't save"));
        }
        return Observable.empty();
    }

    @Override public Observable<Void> updateToDo(ToDo toDo) {
        contentValues.clear();
        contentValues.put(NAME, toDo.name());
        contentValues.put(DESCRIPTION, toDo.description());
        contentValues.put(CATEGORY_ID, toDo.categoryId());
//        int updCount = db.update("mytable", cv, "id = ?", new String[] { id });
        String selection = _ID + " = ?";
        String[] selectionArgs = {"" + toDo.id()};
        int count = database.update(DATABASE_TABLE, contentValues, selection, selectionArgs);
        if(count > 1){
            Timber.wtf("=======>  insert error, count > 1");
        }
        return Observable.empty();
    }

    @Override public Observable<ToDo> getTodo(int id) {
        String[] columns = {_ID, NAME, DESCRIPTION, CATEGORY_ID};
        String selection = _ID + " = ?";
        String[] selectionArgs = {"" + id};
        Cursor cursor = database.query(DATABASE_TABLE, columns, selection, selectionArgs, null, null, null, null);
        if(cursor.moveToFirst()){
            return Observable.just(getToDoFromCursor(cursor));
        }else {
            //return Observable.error(new SQLException("read error or missing id"));
            return Observable.empty();
        }
    }

    @Override public Observable<List<ToDo>> getAllFromCategory(int categoryId) {
        List<ToDo> toDos = new ArrayList<>();
        String[] columns = {_ID, NAME, DESCRIPTION, CATEGORY_ID};
        String selection = CATEGORY_ID + " = ?";
        String[] selectionArgs = {"" + categoryId};
        Cursor cursor = database.query(DATABASE_TABLE, null, selection, selectionArgs, null, null, null, null);
        while (cursor.moveToNext()){
            toDos.add(getToDoFromCursor(cursor));
        }
        return Observable.just(toDos);
    }

    @Override public Observable<Void> saveCategory(ToDoCategory category) {
        return null;
    }

    @Override public Observable<ToDoCategory> getCategory(int id) {
        return null;
    }

    @Override public Observable<List<ToDoCategory>> getAllCategories() {
        return null;
    }

    @Override public Observable<Void> saveProduct(Product product) {
        return null;
    }

    @Override public Observable<List<String>> findProducts(String nameSnippet) {
        return null;
    }

    @Override public Observable<Void> savePurchasingList(PurchasingList list) {
        return null;
    }

    @Override public Observable<List<PurchasingList>> getAllPurchasingLists() {
        return null;
    }

    private ToDo getToDoFromCursor(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(_ID);
        int nameIndex = cursor.getColumnIndex(NAME);
        int descriptionIndex = cursor.getColumnIndex(DESCRIPTION);
        int categoryIndex = cursor.getColumnIndex(CATEGORY_ID);
        return ToDo.builder()
                .id(cursor.getInt(idIndex))
                .name(cursor.getString(nameIndex))
                .description(cursor.getString(descriptionIndex))
                .categoryId(cursor.getInt(categoryIndex))
                .build();
    }
}
