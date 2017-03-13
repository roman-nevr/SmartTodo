package org.berendeev.roma.smarttodo.data.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.berendeev.roma.smarttodo.domain.model.Product;
import org.berendeev.roma.smarttodo.domain.model.PurchasingList;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import timber.log.Timber;

import static android.provider.BaseColumns._ID;
import static org.berendeev.roma.smarttodo.data.datasource.DatabaseOpenHelper.CATEGORIES_TABLE;
import static org.berendeev.roma.smarttodo.data.datasource.DatabaseOpenHelper.CATEGORY_ID;
import static org.berendeev.roma.smarttodo.data.datasource.DatabaseOpenHelper.IS_CHECKED;
import static org.berendeev.roma.smarttodo.data.datasource.DatabaseOpenHelper.IS_EXPANDED;
import static org.berendeev.roma.smarttodo.data.datasource.DatabaseOpenHelper.TODOS_TABLE;
import static org.berendeev.roma.smarttodo.data.datasource.DatabaseOpenHelper.TODO_DESCRIPTION;
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

    @Override public Completable saveToDo(ToDo toDo) {
        fillContentValuesFromToDo(toDo);
        long rowId = database.insert(TODOS_TABLE, null, contentValues);
        if(rowId == -1){
            return Completable.error(new SQLException("can't save"));
        }
        return Completable.complete();
    }

    @Override public Completable updateToDo(ToDo toDo) {
        fillContentValuesFromToDo(toDo);
        String selection = _ID + " = ?";
        String[] selectionArgs = {"" + toDo.id()};
        int count = database.update(TODOS_TABLE, contentValues, selection, selectionArgs);
        if(count > 1){
            Timber.wtf("=======>  insert error, count > 1");
        }
        return Completable.complete();
    }

    @Override public Completable deleteToDo(ToDo toDo) {
        String selection = _ID + " = ? AND " + IS_CHECKED + " = ?";
        String[] selectionArgs = {"" + toDo.id(), "" + getSQLIntegerFromBoolean(true)};
        database.delete(TODOS_TABLE, selection, selectionArgs);
        return Completable.complete();
    }

    @Override public Observable<ToDo> getTodo(int id) {
        String selection = _ID + " = ?";
        String[] selectionArgs = {"" + id};
        Cursor cursor = database.query(TODOS_TABLE, null, selection, selectionArgs, null, null, null, null);
        if(cursor.moveToFirst()){
            return Observable.just(getToDoFromCursor(cursor));
        }else {
            //return Observable.error(new SQLException("read error or missing id"));
            return Observable.empty();
        }
    }

    @Override public Observable<List<ToDo>> getAllFromCategory(int categoryId) {
        List<ToDo> toDos = new ArrayList<>();
        String selection = CATEGORY_ID + " = ?";
        String[] selectionArgs = {"" + categoryId};
        Cursor cursor = database.query(TODOS_TABLE, null, selection, selectionArgs, null, null, null, null);
        while (cursor.moveToNext()){
            toDos.add(getToDoFromCursor(cursor));
        }
        return Observable.just(toDos);
    }

    @Override public Completable saveCategory(ToDoCategory category) {
        contentValues.clear();
        contentValues.put(NAME, category.name());
        contentValues.put(IS_EXPANDED, category.isExpanded() ? 1 : 0);
        long rowId = database.insert(CATEGORIES_TABLE, null, contentValues);
        if(rowId == -1){
            return Completable.error(new SQLException("can't save"));
        }
        return Completable.complete();
    }

    @Override public Completable updateCategory(ToDoCategory category) {
        contentValues.clear();
        contentValues.put(NAME, category.name());
        contentValues.put(IS_EXPANDED, category.isExpanded());
        String selection = _ID + " = ?";
        String[] selectionArgs = {"" + category.id()};
        int count = database.update(CATEGORIES_TABLE, contentValues, selection, selectionArgs);
        if(count > 1){
            Timber.wtf("=======>  insert error, count > 1");
        }
        return Completable.complete();
    }

    @Override public Completable deleteCategory(ToDoCategory category) {
        return null;
    }

    @Override public Observable<ToDoCategory> getCategory(int id) {
        String selection = _ID + " = ?";
        String[] selectionArgs = {"" + id};
        Cursor cursor = database.query(CATEGORIES_TABLE, null, selection, selectionArgs, null, null, null, null);
        if(cursor.moveToFirst()){
            return Observable.just(getCategoryFromCursor(cursor));
        }else {
            //return Observable.error(new SQLException("read error or missing id"));
            return Observable.empty();
        }
    }

    @Override public Observable<List<ToDoCategory>> getAllCategories() {
        List<ToDoCategory> categories = new ArrayList<>();
        Cursor cursor = database.query(TODOS_TABLE, null, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            categories.add(getCategoryFromCursor(cursor));
        }
        return Observable.just(categories);
    }

    @Override public Completable saveProduct(Product product) {
        return null;
    }

    @Override public Observable<List<String>> findProducts(String nameSnippet) {
        return null;
    }

    @Override public Completable savePurchasingList(PurchasingList list) {
        return null;
    }

    @Override public Observable<List<PurchasingList>> getAllPurchasingLists() {
        return null;
    }

    private ToDo getToDoFromCursor(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(_ID);
        int nameIndex = cursor.getColumnIndex(NAME);
        int descriptionIndex = cursor.getColumnIndex(TODO_DESCRIPTION);
        int isCheckedIndex = cursor.getColumnIndex(IS_CHECKED);
        int categoryIndex = cursor.getColumnIndex(CATEGORY_ID);
        return ToDo.builder()
                .id(cursor.getInt(idIndex))
                .name(cursor.getString(nameIndex))
                .description(cursor.getString(descriptionIndex))
                .isChecked(getBooleanFromSQLInteger(cursor.getInt(isCheckedIndex)))
                .categoryId(cursor.getInt(categoryIndex))
                .build();
    }

    private ToDoCategory getCategoryFromCursor(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(_ID);
        int nameIndex = cursor.getColumnIndex(NAME);
        int isExpandedIndex = cursor.getColumnIndex(IS_EXPANDED);
        return ToDoCategory.builder()
                .id(cursor.getInt(idIndex))
                .name(cursor.getString(nameIndex))
                .isExpanded(getBooleanFromSQLInteger(cursor.getInt(isExpandedIndex)))
                .build();
    }

    private boolean getBooleanFromSQLInteger(int anInt) {
        return anInt == 1;
    }

    private int getSQLIntegerFromBoolean(boolean bool){
        return bool ? 1:0;
    }

    private void fillContentValuesFromToDo(ToDo toDo) {
        contentValues.clear();
        contentValues.put(NAME, toDo.name());
        contentValues.put(TODO_DESCRIPTION, toDo.description());
        contentValues.put(CATEGORY_ID, toDo.categoryId());
        contentValues.put(IS_CHECKED, getSQLIntegerFromBoolean(toDo.isChecked()));
    }
}
