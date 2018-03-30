package com.mycabbages.teamavatar.ido;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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
        Log.d(TAG, "signing up user");
        // sign the user up with firebase helper code, then add the user to realtime database
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Log.d(TAG, "createUserWithEmail:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //TODO: recover from auth failure gracefully
                        }

                        // ...
                    }
                    // add user to database and set their display name.
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mUser = mAuth.getCurrentUser();
                        if (mUser != null) {
                            Log.d(TAG, "user is logged in, " +
                                    "auth state changed, adding them to database");
                            // access currently signed in user here...

                            // set the user's display name
                            UserProfileChangeRequest profileUpdates =
                                    new UserProfileChangeRequest.Builder().
                                            setDisplayName(firstName + " " + lastName).build();

                            mUser.updateProfile(profileUpdates);

                            final User user = new User(firstName, lastName, email);

                            final DatabaseReference userRef = mDatabase
                                    .child("users")
                                    .child(mUser.getUid());

                            // save this new user in database under "users" node.
                            userRef.setValue(user);
                        } else {
                            Log.d(TAG, "user is not signed in...");
                        }
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
     * IS USER SIGNED IN
     * @return true if user is signed in, else return false.
     * */
    public boolean isUserSignedIn() {
        if (mAuth.getCurrentUser() != null) {
            Log.d(TAG, mAuth.getCurrentUser().getEmail());
            return true;
        } else {
            return false;
        }
    }

    /**
     * SIGN OUT
     * Sign out of current session.
     * */
    public void signOut() {
        mAuth.signOut();
    }


    @Override
    public void execute(@NonNull Runnable command) {
        Log.d(TAG, "trying to execute...");

    }
}
