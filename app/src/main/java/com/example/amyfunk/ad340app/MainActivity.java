package com.example.amyfunk.ad340app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myapplication.MESSAGE";
    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;

    String[] arr = {"Zombie", "About", "not this one", "stapphhhh"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(new ButtonAdapter(this));

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        EditText editText = findViewById(R.id.editText);
        final Button button = findViewById(R.id.button);
        editText.setText(prefs.getString("autoSave", ""));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()==0){
                    button.setEnabled(false);
                }else{
                    button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                prefs.edit().putString("autoSave", s.toString()).commit();
            }
        });


        // handle navigation drawer events
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        Intent intent;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_movies:
                                Log.d(TAG, menuItem.toString());
                                intent = new Intent(MainActivity.this, RecyclerActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_about:
                                intent = new Intent(MainActivity.this, About.class);
                                startActivity(intent);
                        }

                        return true;
                    }
                });
    }


    public void sendMessage(View view) {

        Intent intent = new Intent(this, DetailActivity.class);
        EditText editText = findViewById(R.id.editText);
        String message = editText.getText().toString();
        Log.d(TAG, "info button " + message);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public boolean inputIsValid(String str) {
        if (str.length() == 0) {
            return false;
        }
        return true;
    }

    public class ButtonAdapter extends BaseAdapter {
        private Context context;

        public ButtonAdapter(Context c) {
            context = c;
        }

        public int getCount() {
            return arr.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Button button;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                button = new Button(context);
            } else {
                button = (Button) convertView;
            }

            button.setText(arr[position]);
            button.setId(position);
            button.setOnClickListener(new BtnOnClickListener());
            return button;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    class BtnOnClickListener implements View.OnClickListener {

        public void onClick(View v) {
            Log.d(TAG, "tapped button");
            int id = v.getId();
            Intent intent;
            switch (id) {
                case 0:
                    intent = new Intent(getBaseContext(), RecyclerActivity.class);
                    startActivity(intent);
                    break;

                case 1:
                    intent = new Intent(getBaseContext(), About.class);
                    startActivity(intent);
                    break;
                default:
                    Button b = (Button) v;
                    String label = b.getText().toString();
                    Toast.makeText(MainActivity.this, label,
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}