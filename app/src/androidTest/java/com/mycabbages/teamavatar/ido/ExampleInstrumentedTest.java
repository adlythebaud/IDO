package com.mycabbages.teamavatar.ido;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.FirebaseApp;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

//        assertEquals("com.mycabbages.teamavatar.ido", appContext.getPackageName());

        FirebaseApp.initializeApp(appContext);
        FirebaseHelper fH = new FirebaseHelper();

        assertEquals(null, fH.getmAuth());

    }
}
