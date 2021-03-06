package org.berendeev.roma.smarttodo;

import org.berendeev.roma.smarttodo.data.datasource.DatabaseOpenHelper;
import org.berendeev.roma.smarttodo.data.datasource.SQLiteDatasource;
import org.berendeev.roma.smarttodo.domain.model.ToDo;
import org.berendeev.roma.smarttodo.domain.model.ToDoCategory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;


@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
public class Database {

    private SQLiteDatasource dataSource;

    @Before
    public void before(){
        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(RuntimeEnvironment.application.getApplicationContext());
        dataSource = new SQLiteDatasource(openHelper);
    }

    @Test
    public void readWriteToDoTest(){
        ToDo toDo = generateToDo(1, 0);
        dataSource.saveToDo(toDo);
        dataSource.getTodo(1).subscribe(toDo1 -> {
            Assert.assertTrue(toDo.equals(toDo1));
            System.out.println("readWriteToDoTest complete");
        });
    }

    @Test
    public void getAllFromCategoryTest(){
        List<ToDo> toDos = new ArrayList<>();
        ToDo toDo1 = generateToDo(1, 0);
        ToDo toDo2 = generateToDo(2, 0);
        toDos.add(toDo1);
        toDos.add(toDo2);
        dataSource.saveToDo(toDo1);
        dataSource.saveToDo(toDo2);
//        dataSource.getAllFromCategory(0)
//                .subscribe(toDos2 -> {
//                    Assert.assertTrue(toDos.equals(toDos2));
//                    System.out.println("getAllFromCategoryTest complete");
//                });
    }

    @Test
    public void readWriteCategoryTest(){
        ToDoCategory category = generateCategory(1);
        dataSource.saveCategory(category);
        dataSource.getCategory(1).subscribe(category1 -> {
            Assert.assertTrue(category.equals(category1));
            System.out.println("readWriteCategoryTest complete");
        });

    }

    private ToDo generateToDo(int id, int category){
        return ToDo.builder()
                .id(id)
                .name("test " + id)
                .description("desc " + id)
                .categoryId(category)
                .isChecked(true)
                .build();
    }

    private ToDoCategory generateCategory(int id){
        return ToDoCategory.builder()
                .id(id)
                .name("Category " + id)
                .isExpanded(true)
                .build();
    }

    @After
    public void waitAfter(){

    }
}
