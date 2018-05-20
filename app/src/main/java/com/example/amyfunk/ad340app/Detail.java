package com.example.amyfunk.ad340app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import static com.example.amyfunk.ad340app.RecyclerActivity.DESCRIPTION;
import static com.example.amyfunk.ad340app.RecyclerActivity.DIRECTOR;
import static com.example.amyfunk.ad340app.RecyclerActivity.IMAGE;
import static com.example.amyfunk.ad340app.RecyclerActivity.TITLE;
import static com.example.amyfunk.ad340app.RecyclerActivity.YEAR;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //get String value from the selected item
        Intent intent = getIntent();
        String zmTitle = intent.getStringExtra(TITLE);
        String zmYear = intent.getStringExtra(YEAR);
        String zmDirector = intent.getStringExtra(DIRECTOR);
        String zmImage = intent.getStringExtra(IMAGE);
        String zmDescription = intent.getStringExtra(DESCRIPTION);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setTitle(zmTitle);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RecyclerActivity.class));
                finish();
            }
        });




        TextView title = findViewById(R.id.title);
        TextView year = findViewById(R.id.year);
        TextView director = findViewById(R.id.director);
        ImageView image = findViewById(R.id.image);
        TextView description = findViewById(R.id.description);

        //set the values to each item in the model
        Picasso.get().load(zmImage).fit().into(image);
        title.setText(zmTitle);
        year.setText("In " + zmYear);
        director.setText(zmDirector);
        description.setText(zmDescription);
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
            Toast.makeText(Detail.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}