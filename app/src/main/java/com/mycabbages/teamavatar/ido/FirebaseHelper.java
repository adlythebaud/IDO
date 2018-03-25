package com.mycabbages.teamavatar.ido;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

/**
 * Created by adlythebaud on 3/20/18.
 */

public class FirebaseHelper implements Executor {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;        // this is our database reference.
    private FirebaseUser mUser;
    private final String TAG = "FirebaseHelper";

    public FirebaseHelper() {
        mAuth = FirebaseAuth.getInstance();
        // set your DatabaseReference object to our current database.
        mDatabase = FirebaseDatabase.getInstance().getReference();
        
        
    }

    /**
     * SIGN UP USER
     * Signs the user up as a new user and adds them to database.
     * */
    public void signUpUser(final String firstName, final String lastName, final String email, String password) {
        // sign the user up with firebase helper code, then add the user to realtime database
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //TODO: recover from auth failure gracefully
                        }

                        // ...
                    }
                });
    }

    public void signInUser(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            // FirebaseUser user = mAuth.getCurrentUser();
                            //TODO: set up users' UI with correct goals.
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "signInWithEmail:failure", task.getException());
                            //TODO: recover from auth failure gracefully
                        }

                        // ...
                    }
                });
    }

    /**
     * SIGN OUT
     * Sign out of current session.
     * */
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void execute(@NonNull Runnable command) {
        Log.d(TAG, "trying to execute...");
    }

    /**
    * Adds a new user to realtime database
    */
    public void addUserToDatabase(String firstName, String lastName, String email) {

        Log.d(TAG, "adding new user to database");
        // Create a User object to store in the database
        final User user = new User(firstName, lastName, email);

        // create a new reference under the users in FB, add the user to the database
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        //TODO: make sure the Uid in database and authentication pages are the same per user.
        final DatabaseReference userRef = mDatabase.child("users").child(mUser.getUid());

        // save this new user in firebase database tree under "users" child tree.
        userRef.setValue(user);

    }
}
