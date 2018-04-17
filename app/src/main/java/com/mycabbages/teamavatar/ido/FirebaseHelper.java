package com.mycabbages.teamavatar.ido;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

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
    private Context mContext;
    private final String TAG = "FirebaseHelper";
    private boolean signInResult;


    /**
     * FIREBASEHELPER CONSTRUCTOR
     * */
    public FirebaseHelper() {
        mAuth = FirebaseAuth.getInstance();
        // set your DatabaseReference object to our current database.
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public FirebaseHelper(Context context) {
        this.mContext = context;
        mAuth = FirebaseAuth.getInstance();
        // set your DatabaseReference object to our current database.
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    /**
     * SIGN UP USER
     * Signs the user up as a new user and adds them to database.
     * @param email user's email address
     * @param password user's password
     * @param firstName user's firstname needed for displayname
     * @param lastName user's lastname needed for displayname
     * */
    public void signUpUser(final String firstName, final String lastName, final String email, String password) {
        Log.d(TAG, "signing up user");
        // sign the user up with firebase helper code, then add the user to realtime database
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            // access currently signed in user here...
                            mUser = mAuth.getCurrentUser();
                            if (mUser != null) {
                                Log.d(TAG, "user is logged in, " +
                                        "auth state changed, adding them to database");
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
                                setmUser(mUser);
                            } else {
                                Log.d(TAG, "user is not signed in...");
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //TODO: recover from auth failure gracefully
                        }
                    }
                });

    }

    /**
     * SIGN IN USER
     * @param email
     * @param password
     * */
    public boolean signInUser(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            signInResult = true;
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            // FirebaseUser user = mAuth.getCurrentUser();
                            //TODO: set up users' UI with correct goals.
                            Log.d(TAG, "User from task: " +task.getResult().getUser().getEmail());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "signInWithEmail:failure", task.getException());
                            //TODO: recover from auth failure gracefully
                            if (mContext != null) {
                                Toast.makeText(mContext
                                        , task.getException().getMessage()
                                        , Toast.LENGTH_SHORT)
                                        .show();
                            }

                            // the sign in result is false.
                            signInResult = false;
                        }

                    }
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if (authResult.getUser() != null) {
                            // user is signed in
                            signInResult = true;
                            setmUser(authResult.getUser());
                        } else {
                            // user is not signed in, error handle.
                            signInResult = false;
                        }
                    }
                });
        return signInResult;
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
        removeUserDataFromSharedPreferences();
        mAuth.signOut();
    }

    /**
     * GET AUTH
     * */
    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    /**
     * GET USER
     * */
    public FirebaseUser getmUser() {
        return mUser;
    }

    /**
     * SET USER
     * */
    public void setmUser(FirebaseUser mUser) {
        this.mUser = mUser;
        saveUserDataInSharedPreferences(mUser);
    }

    /**
     * SET USER DATA
     * */
    public void saveUserDataInSharedPreferences(FirebaseUser mUser) {
//        SharedPreferences pref =
//                PreferenceManager.getDefaultSharedPreferences(mContext);
//        SharedPreferences.Editor edit = pref.edit();
//        edit.putString("userDisplayName", mUser.getDisplayName());
//        edit.putString("userEmail", mUser.getEmail());
//        edit.putString("userID", mUser.getUid());
//        edit.apply();
        SharedPreferences sharedPreferences =
                mContext.getSharedPreferences("FirebaseHelper", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("userDisplayName", mUser.getDisplayName());
        edit.putString("userEmail", mUser.getEmail());
        edit.putString("userID", mUser.getUid());
        edit.apply();
    }

    public void removeUserDataFromSharedPreferences() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("FirebaseHelper", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear();
        edit.apply();
    }

    public String getUserDisplayNameFromSharedPreferences() {
        SharedPreferences pref =
                mContext.getSharedPreferences("FirebaseHelper", Context.MODE_PRIVATE);
        return pref.getString("userDisplayName", "n/a");
    }

    public String getUserEmailFromSharedPreferences() {
        SharedPreferences pref =
                mContext.getSharedPreferences("FirebaseHelper", Context.MODE_PRIVATE);
        return pref.getString("userEmail", "n/a");
    }

    public String getUserIDFromSharedPreferences() {
        SharedPreferences pref =
                mContext.getSharedPreferences("FirebaseHelper", Context.MODE_PRIVATE);
        return pref.getString("userID", "n/a");
    }

    /**
     * EXECUTE
     * needed for Executor interface
     * */
    @Override
    public void execute(@NonNull Runnable command) {
        Log.d(TAG, "trying to execute...");

    }
}
