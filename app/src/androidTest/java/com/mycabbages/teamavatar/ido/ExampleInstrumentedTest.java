package com.mycabbages.teamavatar.ido;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;
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
        final FirebaseHelper fH = new FirebaseHelper();

        assertEquals("https://ido-ratcliffe.firebaseio.com",
                FirebaseDatabase.getInstance().getReference().toString());

        FirebaseAuth.AuthStateListener fAuth = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = fH.getmAuth().getCurrentUser();
//                assertEquals("thebaud@gmail.com", firebaseAuth.getInstance().getCurrentUser().getEmail());
//                assertEquals("Andre Ingram", user.getDisplayName());
                assertNotNull(fH.getCurrentUser());
            }

        };

        fH.getmAuth().addAuthStateListener(fAuth);


        fH.signInUser("andreingram@gmail.com", "foodeater123");
//        assertNotNull(fH.getCurrentUser());
//        assert(fH.isUserSignedIn() == true);

    }
}
