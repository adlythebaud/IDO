package com.mycabbages.teamavatar.ido;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
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
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;
    private Context mContext;
    private final String TAG = "FirebaseHelper";
    private final String AUTHTAG = "LoginFlow";
    public boolean signInResult;
    public Executor mExecutor;
    public OnCompleteListener<AuthResult> onCompleteListener;



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
        mExecutor = new Executor() {
            @Override
            public void execute(@NonNull Runnable command) {
                command.run();
            }
        };
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

    public DatabaseReference getmDatabase() {
        return mDatabase;
    }

    public void setmDatabase(DatabaseReference mDatabase) {
        this.mDatabase = mDatabase;
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
        command.run();
        Log.d(AUTHTAG, "Execute called. Calling command.run().");
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
