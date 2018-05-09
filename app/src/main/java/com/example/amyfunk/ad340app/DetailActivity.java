package com.example.amyfunk.ad340app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Message");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Log.d(TAG, "detail " + message);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_settings){
            Toast.makeText(DetailActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();  // Always call the superclass method first
        Log.d(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Log.d(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        Log.d(TAG, "onPause()");
    }
    @Override
    public void onStop() {
        super.onStop();  // Always call the superclass method first
        Log.d(TAG, "onStop()");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();  // Always call the superclass method first
        Log.d(TAG, "onDestroy()");
    }
}