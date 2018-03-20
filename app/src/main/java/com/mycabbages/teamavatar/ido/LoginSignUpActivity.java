package com.mycabbages.teamavatar.ido;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * LOGIN ACTIVITY
 */
public class LoginSignUpActivity extends AppCompatActivity {
    public final static String TAG = "LoginSignUpActivity";
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
        firebaseHelper = new FirebaseHelper();
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
    }

    /**
     * AUTHENTICATE
     * Respond to large button being tapped
     * */
    public void authenticate(View view) {
        switch (activityState) {
            case SIGNUP:
                    Log.d(TAG, "Signing up");
                    firebaseHelper.signUpUser(firstNameEditText.getText().toString(), lastNameEditText.getText().toString(),
                            emailEditText.getText().toString(), passwordEditText.getText().toString());
                    goToHome();
                break;
            case SIGNIN:
                Log.d(TAG, "Signing in");
                firebaseHelper.signInUser(emailEditText.getText().toString(), passwordEditText.getText().toString());
                goToHome();
                break;
            case PASSWORDRESET:
                Log.d(TAG, "Resetting password");
                //TODO: Use server side code to sign in a user.
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
        Intent intentToStartMainActivity = new Intent(this,
                MainActivity.class);
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

        // make firstNameEditText gone, unclickable, unfocusable
        firstNameEditText.setVisibility(VISIBLE);
        firstNameEditText.setClickable(true);
        firstNameEditText.setFocusable(true);

        // make lastNameEditText gone, unclickable, unfocusable
        lastNameEditText.setVisibility(VISIBLE);
        lastNameEditText.setClickable(true);
        lastNameEditText.setFocusable(true);

        passwordEditText.setVisibility(VISIBLE);
        passwordEditText.setClickable(true);
        passwordEditText.setFocusable(true);

        showSignInUI.setVisibility(VISIBLE);
        showSignInUI.setClickable(true);

        showPasswordResetUI.setVisibility(VISIBLE);
        showPasswordResetUI.setClickable(true);

        showSignUpUI.setVisibility(GONE);
        showSignUpUI.setClickable(false);


        //TODO: Make sure constraints are properly set up

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

        // make firstNameEditText gone, unclickable, unfocusable
        firstNameEditText.setVisibility(GONE);
        firstNameEditText.setClickable(false);
        firstNameEditText.setFocusable(false);

        // make lastNameEditText gone, unclickable, unfocusable
        lastNameEditText.setVisibility(GONE);
        lastNameEditText.setClickable(false);
        lastNameEditText.setFocusable(false);

        emailEditText.setVisibility(VISIBLE);
        emailEditText.setClickable(true);
        emailEditText.setFocusable(true);

        passwordEditText.setVisibility(VISIBLE);
        passwordEditText.setClickable(true);
        passwordEditText.setFocusable(true);

        showPasswordResetUI.setVisibility(VISIBLE);
        showPasswordResetUI.setClickable(true);

        showSignUpUI.setVisibility(VISIBLE);
        showSignUpUI.setClickable(true);

        showSignInUI.setVisibility(GONE);
        showSignInUI.setClickable(false);



        //TODO: Make sure constraints are properly set up
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
        firstNameEditText.setClickable(false);
        firstNameEditText.setFocusable(false);

        // make lastNameEditText gone, unclickable, unfocusable
        lastNameEditText.setVisibility(GONE);
        lastNameEditText.setClickable(false);
        lastNameEditText.setFocusable(false);

        // make passwordEditText gone, unclickable, unfocusable
        passwordEditText.setVisibility(GONE);
        passwordEditText.setClickable(false);
        passwordEditText.setFocusable(false);

        emailEditText.setVisibility(VISIBLE);
        emailEditText.setClickable(true);
        emailEditText.setFocusable(true);

        // show sign in UI textview
        showSignInUI.setVisibility(VISIBLE);
        showSignInUI.setClickable(true);

        // show sign up UI textview underneath it
        showSignUpUI.setVisibility(VISIBLE);
        showSignUpUI.setClickable(true);

        // hide password reset UI textview;
        showPasswordResetUI.setVisibility(GONE);
        showPasswordResetUI.setClickable(false);

        //TODO: Make sure constraints are properly set up
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
