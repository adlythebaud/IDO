package com.mycabbages.teamavatar.ido;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * LOGIN ACTIVITY
 */
public class LoginSignUpActivity extends AppCompatActivity {
    public final static String TAG = "LoginSignUpActivity";
    private final String AUTHTAG = "LoginFlow";
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button authButton;
    private TextView showSignInUI;
    private TextView showSignUpUI;
    private TextView showPasswordResetUI;
    private SignInState activityState;
    private ConstraintLayout loginConstraintLayout;
    private FirebaseHelper firebaseHelper;

    /**
     * ON CREATE
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        getUIElements();
        firebaseHelper = new FirebaseHelper(this);
        activityState = SignInState.SIGNUP;
        Log.d(TAG, activityState.toString());


    }

    /**
     * ON START
     * Check if the user is already signed in here.
     * */
    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseHelper.isUserSignedIn()) {
            Log.d(AUTHTAG, "user " + firebaseHelper.getmUser().getEmail() + " is signed in");
            goToHome();
        }
    }

    /**
     * AUTHENTICATE
     * Respond to large button being tapped
     * */
    public void authenticate(View view) {
        //TODO: Error handle for if any of the text fields are empty.
        switch (activityState) {
            case SIGNUP:
                    Log.d(TAG, "Signing up");

                    // Sign up user and add them to database.
                    firebaseHelper.signUpUser(firstNameEditText.getText().toString(),
                            lastNameEditText.getText().toString(),
                            emailEditText.getText().toString(),
                            passwordEditText.getText().toString());
                    // go to home screen UI
                    goToHome();
                break;

            case SIGNIN:
//                Log.d(AUTHTAG, "Sign in button clicked from LoginSignUpActivity");
//                firebaseHelper.signInUser(emailEditText.getText().toString(),
//                        passwordEditText.getText().toString());
//                Log.d(AUTHTAG, "From Activity, signInResult: " + firebaseHelper.signInResult);
//                if (firebaseHelper.signInResult) {
//                    Log.d(AUTHTAG, "We're going to main activity");
//                    goToHome();
//
//                }
                Log.d(AUTHTAG, "Sign in button clicked from LoginSignUpActivity");
                firebaseHelper.getmAuth().signInWithEmailAndPassword(emailEditText.getText().toString(),
                        passwordEditText.getText().toString()).addOnCompleteListener(firebaseHelper.mExecutor,
                        new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(AUTHTAG, "signInWithEmail:success");
                            // FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(AUTHTAG, "User from task: " + task.getResult().getUser().getEmail());
                            firebaseHelper.setmUser(task.getResult().getUser());
                            goToHome();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(AUTHTAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getBaseContext()
                                    , task.getException().getMessage()
                                    , Toast.LENGTH_SHORT)
                                    .show();

                        }

                    }
                });
                break;

            case PASSWORDRESET:
                Log.d(TAG, "Resetting password");
                //TODO: Take care of sending password reset email.
                break;

            default:
                Log.d(TAG, "nothing happened");

        }
    }

    /**
     * GO TO HOME
     * Starts intent to MainActivity
     * */
    public void goToHome() {
        Log.d(TAG, "Intent to home called");
        // send coupleID and userID across activities.

        // clear all editText elements
        firstNameEditText.setText("");
        lastNameEditText.setText("");
        emailEditText.setText("");
        passwordEditText.setText("");


        Intent intentToStartMainActivity = new Intent(this,
                MainActivity.class);
        // intentToStartMainActivity.putExtra("userEmail", firebaseHelper.getmUser().getEmail()); // returns null.
        startActivity(intentToStartMainActivity);

    }

    /**
     * GET UI ELEMENTS
     * Hook up all UI Elements
     * */
    public void getUIElements() {
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        authButton = findViewById(R.id.authenticate_button);
        showSignUpUI = findViewById(R.id.showSignUpUI);
        showSignInUI = findViewById(R.id.showSignInUI);
        showPasswordResetUI = findViewById(R.id.showPasswordResetUI);
        loginConstraintLayout = findViewById(R.id.loginConstraintLayout);
    }

    /**
     * DISPLAY SIGN UP UI
     * Changes the UI to display credentials necessary for signing up.
     * */
    public void displaySignUpUI(View view) {
        Log.d(TAG,"Displaying Sign Up UI");

        // make emailEditText visible and editable
        emailEditText.setVisibility(VISIBLE);

        // make passwordEditText visible and editable
        passwordEditText.setVisibility(VISIBLE);

        // make firstNameEditText visible and editable
        firstNameEditText.setVisibility(VISIBLE);

        // make lastNameEditText visible and editable
        lastNameEditText.setVisibility(VISIBLE);

        showSignInUI.setVisibility(VISIBLE);
        showSignInUI.setClickable(true);
        showSignInUI.setEnabled(true);

        showPasswordResetUI.setVisibility(VISIBLE);
        showPasswordResetUI.setClickable(true);
        showPasswordResetUI.setEnabled(true);

        showSignUpUI.setVisibility(GONE);
        showSignUpUI.setClickable(false);
        showSignUpUI.setEnabled(false);


        // Make sure constraints are properly set up
        // Move showPasswordResetUI textview to right place in UI.
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(loginConstraintLayout);
        constraintSet.connect(R.id.showPasswordResetUI,ConstraintSet.TOP,
                R.id.showSignInUI,ConstraintSet.BOTTOM,8);
        constraintSet.applyTo(loginConstraintLayout);

        // change auth button text
        authButton.setText(R.string.sign_up);
        // set the activity state
        activityState = SignInState.SIGNUP;
        Log.d(TAG, activityState.toString());


    }

    /**
     * DISPLAY SIGN IN UI
     * Changes the UI to display credentials necessary for signing in.
     * */
    public void displaySignInUI(View view) {
        Log.d(TAG,"Displaying Sign In UI");

        // make emailEditText visible and editable
        emailEditText.setVisibility(VISIBLE);

        // make passwordEditText visible and editable
        passwordEditText.setVisibility(VISIBLE);

        // make firstNameEditText gone, unclickable, unfocusable
        firstNameEditText.setVisibility(GONE);

        // make lastNameEditText gone, unclickable, unfocusable
        lastNameEditText.setVisibility(GONE);


        showPasswordResetUI.setVisibility(VISIBLE);
        showPasswordResetUI.setClickable(true);
        showPasswordResetUI.setEnabled(true);

        showSignUpUI.setVisibility(VISIBLE);
        showSignUpUI.setClickable(true);
        showSignUpUI.setEnabled(true);

        showSignInUI.setVisibility(GONE);
        showSignInUI.setClickable(false);
        showSignInUI.setEnabled(false);



        // Make sure constraints are properly set up
        // move sign up UI tetview to the right place by changing constraints.
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(loginConstraintLayout);
        constraintSet.connect(R.id.showPasswordResetUI,ConstraintSet.TOP,
                R.id.showSignUpUI,ConstraintSet.BOTTOM,8);
        constraintSet.applyTo(loginConstraintLayout);


        // change auth button text
        authButton.setText(R.string.sign_in);
        // set the activity state
        activityState = SignInState.SIGNIN;
        Log.d(TAG, activityState.toString());
    }

    /**
     * DISPLAY PASSWORD RESET UI
     * */
    public void displayPasswordResetUI(View view) {
        Log.d(TAG, "Displaying password reset UI");

        // make firstNameEditText gone, unclickable, unfocusable
        firstNameEditText.setVisibility(GONE);

        // make lastNameEditText gone, unclickable, unfocusable
        lastNameEditText.setVisibility(GONE);

        // make passwordEditText gone, unclickable, unfocusable
        passwordEditText.setVisibility(GONE);

        emailEditText.setVisibility(VISIBLE);

        // show sign in UI textview
        showSignInUI.setVisibility(VISIBLE);
        showSignInUI.setClickable(true);
        showSignInUI.setEnabled(true);

        // show sign up UI textview underneath it
        showSignUpUI.setVisibility(VISIBLE);
        showSignUpUI.setClickable(true);
        showSignUpUI.setEnabled(true);

        // hide password reset UI textview;
        showPasswordResetUI.setVisibility(GONE);
        showPasswordResetUI.setClickable(false);
        showPasswordResetUI.setEnabled(false);

        // Make sure constraints are properly set up
        // move sign up UI tetview to the right place by changing constraints.
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(loginConstraintLayout);
        constraintSet.connect(R.id.showSignUpUI,ConstraintSet.TOP,
                R.id.showSignInUI,ConstraintSet.BOTTOM,8);
        constraintSet.applyTo(loginConstraintLayout);

        // change main button text
        authButton.setText(R.string.reset_password);
        // set the activity state
        activityState = SignInState.PASSWORDRESET;
        Log.d(TAG, activityState.toString());
    }

}
