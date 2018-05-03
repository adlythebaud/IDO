package com.mycabbages.teamavatar.ido;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
    private final String AUTHTAG = "LoginFlow";
    private boolean signInResult;
    public Executor mExecutor;



    /**
     * FIREBASEHELPER CONSTRUCTOR
     * */
    public FirebaseHelper() {
        mAuth = FirebaseAuth.getInstance();
        // set your DatabaseReference object to our current database.
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        signInResult = Boolean.FALSE;
    }

    public FirebaseHelper(Context context) {
        this.mContext = context;
        mAuth = FirebaseAuth.getInstance();
        // set your DatabaseReference object to our current database.
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        signInResult = Boolean.FALSE;
    }

    public FirebaseHelper(Context context, Executor executor) {
        this.mContext = context;
        this.mExecutor = executor;
        mAuth = FirebaseAuth.getInstance();
        // set your DatabaseReference object to our current database.
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        signInResult = Boolean.FALSE;
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
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(AUTHTAG, "createUserWithEmail:success");
                            // access currently signed in user here...
                            mUser = mAuth.getCurrentUser();
                            if (mUser != null) {
                                Log.d(AUTHTAG, "user is logged in, " +
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
                                Log.d(AUTHTAG, "user is not signed in...");
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(AUTHTAG, "createUserWithEmail:failure", task.getException());
                            //TODO: recover from auth failure gracefully
                            if (mContext != null) {
                                Toast.makeText(mContext
                                        , task.getException().getMessage()
                                        , Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    }
                });

    }

    /**
     * SIGN IN USER
     * @param email
     * @param password
     * @return signInResult - the result of whether or not the authentication was successful
     * */
    public boolean signInUser(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(AUTHTAG, "signInWithEmail:success");
                            // FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(AUTHTAG, "User from task: " + task.getResult().getUser().getEmail());
                            signInResult = Boolean.TRUE;
                            setmUser(task.getResult().getUser());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(AUTHTAG, "signInWithEmail:failure", task.getException());
                            if (mContext != null) {
                                Toast.makeText(mContext
                                        , task.getException().getMessage()
                                        , Toast.LENGTH_SHORT)
                                        .show();
                            }
                            signInResult = Boolean.FALSE;
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
        return mAuth.getCurrentUser() != null;
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
        Log.d(AUTHTAG, "Execute called. Calling command.run().");
        command.run();
    }

    /*
    * TODO:
    * I want to click the Sign In button,
    * authenticate the user,
    * get a result that says whether the sign in worked or not,
    * and then finally go to the main activity.
    * IN THAT ORDER.
    * */
}
