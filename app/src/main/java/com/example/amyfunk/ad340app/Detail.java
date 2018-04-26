package com.example.amyfunk.ad340app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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


        TextView title = findViewById(R.id.title);
        TextView year = findViewById(R.id.year);
        TextView director = findViewById(R.id.director);
        ImageView image = findViewById(R.id.image);
        TextView description = findViewById(R.id.description);

        //set the values to each item in the model
        Picasso.with(this).load(zmImage).fit().into(image);
        title.setText(zmTitle);
        year.setText("In " + zmYear);
        director.setText(zmDirector);
        description.setText(zmDescription);
    }
}