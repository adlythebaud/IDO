package com.mycabbages.teamavatar.ido;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ListView list;
    String[] list_items = {
            "Kiss your wife passionately, go have fun and play frisbee woohooo",
            "Have a deep meaningful conversation about your fears and concerns",
            "Take your wife out on a fancy date, secure a baby sitter",
            "Send a small thinking of you gift, something small yet meaningful"
    };
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MainActivityListAdapter listAdapter = new
                MainActivityListAdapter(MainActivity.this, list_items);
        list = (ListView)findViewById(R.id.mainListView);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent changeItemActivity = new Intent(MainActivity.this, ChangeItemActivity.class);
                TextView title = (TextView) view.findViewById(R.id.item_title);
                changeItemActivity.putExtra("Title", title.getText().toString());
                startActivity(changeItemActivity);

            }
        });

        final Intent intent = new Intent(this, NewItemActivity.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.user_list_item) {
            return true;
        } else if (id == R.id.notifications_settings) {
            Intent intent = new Intent(this, NotifSettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.sign_out) {
            FirebaseHelper firebaseHelper = new FirebaseHelper();
            firebaseHelper.signOut();
            Intent intent = new Intent(this,
                    LoginSignUpActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void button(View view) {
        Intent intentToGoToAddActivity = new Intent(this, NewItemActivity.class);
        startActivity(intentToGoToAddActivity);

    }
}
