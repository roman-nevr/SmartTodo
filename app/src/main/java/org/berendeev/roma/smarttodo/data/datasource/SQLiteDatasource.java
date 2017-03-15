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

public class SQLiteDatasource {

    private final DatabaseOpenHelper openHelper;
    private final ContentValues contentValues;
    private SQLiteDatabase database;

    public SQLiteDatasource(DatabaseOpenHelper openHelper) {
        this.openHelper = openHelper;
        database = openHelper.getWritableDatabase();
        contentValues = new ContentValues();
    }

    public Completable saveToDo(ToDo toDo) {
        return Completable.create(e -> {
            fillContentValuesFromToDo(toDo);
            long rowId = database.insert(TODOS_TABLE, null, contentValues);
            if (rowId == -1) {
                Completable.error(new SQLException("can't save"));
            }
            Completable.complete();
        });

    }

    public Completable updateToDo(ToDo toDo) {
        return Completable.create(emitter -> {
            fillContentValuesFromToDo(toDo);
            String selection = _ID + " = ?";
            String[] selectionArgs = {"" + toDo.id()};
            int count = database.update(TODOS_TABLE, contentValues, selection, selectionArgs);
            if(count > 1){
                Timber.wtf("=======>  insert error, count > 1");
            }
            if (!emitter.isDisposed()){
                emitter.onComplete();
            }
        });
    }

    public Completable deleteToDo(ToDo toDo) {
        return Completable.create(e -> {
            String selection = _ID + " = ? AND " + IS_CHECKED + " = ?";
            String[] selectionArgs = {"" + toDo.id(), "" + getSQLIntegerFromBoolean(true)};
            database.delete(TODOS_TABLE, selection, selectionArgs);
            Completable.complete();
        });
    }

    public Observable<ToDo> getTodo(int id) {
        return Observable.create(emitter -> {
            String selection = _ID + " = ?";
            String[] selectionArgs = {"" + id};
            Cursor cursor = database.query(TODOS_TABLE, null, selection, selectionArgs, null, null, null, null);
            if(cursor.moveToFirst()){
                emitter.onNext(getToDoFromCursor(cursor));
            }else {
                //return Observable.error(new SQLException("read error or missing id"));
                emitter.onComplete();
            }
            cursor.close();
        });
    }

    public Observable<List<ToDo>> getAllFromCategory(int categoryId) {
        return Observable.create(emitter -> {
            List<ToDo> toDos = new ArrayList<>();
            String selection = CATEGORY_ID + " = ?";
            String[] selectionArgs = {"" + categoryId};
            Cursor cursor = database.query(TODOS_TABLE, null, selection, selectionArgs, null, null, null, null);
            while (cursor.moveToNext()){
                toDos.add(getToDoFromCursor(cursor));
            }
            cursor.close();
            if(!emitter.isDisposed()){
                emitter.onNext(toDos);
                emitter.onComplete();
            }
        });

    }

    public Observable<List<ToDo>> getAllToDos() {
        return Observable.create(emitter -> {
            if(!emitter.isDisposed()){
                emitter.onNext(loadAllToDos());
                emitter.onComplete();
            }
        });
    }

    public Completable saveCategory(ToDoCategory category) {
        return Completable.create(e -> {
            contentValues.clear();
            contentValues.put(NAME, category.name());
            contentValues.put(IS_EXPANDED, category.isExpanded() ? 1 : 0);
            long rowId = database.insert(CATEGORIES_TABLE, null, contentValues);
            if(rowId == -1){
                e.onError(new SQLException("can't save"));
            }
            e.onComplete();
        });
    }

    public Completable updateCategory(ToDoCategory category) {
        return Completable.create(e -> {
            contentValues.clear();
            contentValues.put(NAME, category.name());
            contentValues.put(IS_EXPANDED, category.isExpanded());
            String selection = _ID + " = ?";
            String[] selectionArgs = {"" + category.id()};
            int count = database.update(CATEGORIES_TABLE, contentValues, selection, selectionArgs);
            if(count > 1){
                Timber.wtf("=======>  insert error, count > 1");
            }
            e.onComplete();
        });
    }

    public Completable deleteCategory(ToDoCategory category) {
        return Completable.create(e -> {
            String selection = _ID + " = ?";
            String[] selectionArgs = {"" + category.id()};
            int count = database.delete(CATEGORIES_TABLE, selection, selectionArgs);
            if (count > 1){
                e.onError(new SQLException());
            }else {
                e.onComplete();
            }
        });
    }

    public Observable<ToDoCategory> getCategory(int id) {
        return Observable.create(emitter -> {
            String selection = _ID + " = ?";
            String[] selectionArgs = {"" + id};

            List<ToDo> toDos = loadAllToDos();
            Cursor cursor = database.query(CATEGORIES_TABLE, null, selection, selectionArgs, null, null, null, null);
            if(cursor.moveToFirst()){
                emitter.onNext(getCategoryFromCursor(cursor, toDos));
            }else {
                //return Observable.error(new SQLException("read error or missing id"));
            }
            emitter.onComplete();
            cursor.close();
        });
    }

    public Observable<List<ToDoCategory>> getAllCategories() {
        return Observable.create(e -> {
            List<ToDo> toDos = new ArrayList<>();
            Cursor cursor = database.query(TODOS_TABLE, null, null, null, null, null, null, null);
            while (cursor.moveToNext()){
                toDos.add(getToDoFromCursor(cursor));
            }
            cursor.close();

            List<ToDoCategory> categories = new ArrayList<>();
            cursor = database.query(CATEGORIES_TABLE, null, null, null, null, null, null, null);
            while (cursor.moveToNext()){
                ToDoCategory category = getCategoryFromCursor(cursor, toDos);

                categories.add(category);
            }
            e.onNext(categories);
            cursor.close();
        });
    }

    public Completable saveProduct(Product product) {
        return null;
    }

    public Observable<List<String>> findProducts(String nameSnippet) {
        return null;
    }

    public Completable savePurchasingList(PurchasingList list) {
        return null;
    }

    public Observable<List<PurchasingList>> getAllPurchasingLists() {
        return null;
    }

    private List<ToDo> loadAllToDos(){
        List<ToDo> toDos = new ArrayList<>();
        Cursor cursor = database.query(TODOS_TABLE, null, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            toDos.add(getToDoFromCursor(cursor));
        }
        cursor.close();
        return toDos;
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

    private ToDoCategory getCategoryFromCursor(Cursor cursor, List<ToDo> toDos) {

        int idIndex = cursor.getColumnIndex(_ID);
        int nameIndex = cursor.getColumnIndex(NAME);
        int isExpandedIndex = cursor.getColumnIndex(IS_EXPANDED);

        int categoryId = cursor.getInt(idIndex);

        List<ToDo> categoryToDo = new ArrayList<>();
        for (ToDo toDo : toDos) {
            if (toDo.categoryId() == categoryId){
                categoryToDo.add(toDo);
            }
        }
        return ToDoCategory.builder()
                .id(categoryId)
                .name(cursor.getString(nameIndex))
                .isExpanded(getBooleanFromSQLInteger(cursor.getInt(isExpandedIndex)))
                .toDos(categoryToDo)
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
