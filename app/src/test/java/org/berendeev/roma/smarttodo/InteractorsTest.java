package org.berendeev.roma.smarttodo;

import android.app.Activity;

import org.berendeev.roma.smarttodo.presentation.App;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        manifest="../app/src/main/AndroidManifest.xml",
        resourceDir = "../app/src/main/res")
public class InteractorsTest {

    @Before
    public void before(){
       //App app = (App) RuntimeEnvironment.application;
        Activity activity = Robolectric.setupActivity(MainActivity.class);
        App app = (App)(activity.getApplication());
    }

    @Test
    public void saveInteractorTest(){

    }
}
