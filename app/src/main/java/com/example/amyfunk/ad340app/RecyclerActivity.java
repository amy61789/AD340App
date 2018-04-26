package com.example.amyfunk.ad340app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class RecyclerActivity extends AppCompatActivity implements Adapter.OnItemClickListener {
    public static final String TITLE = "title";
    public static final String DIRECTOR = "unknown";
    public static final String YEAR = "year";
    public static final String IMAGE = "url";
    public static final String DESCRIPTION= "description";

    Context context;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    Adapter adapter;
    RequestQueue queue;

    String url = "http://stakada.icoolshow.net/zombie_movies.json";

    ArrayList<Movies> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        context = getApplicationContext();

        recyclerView = findViewById(R.id.recyclerview1);
        recyclerViewLayoutManager = new LinearLayoutManager(context);

        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);


        queue = Volley.newRequestQueue(this);
        parseJSON();
    }
    private void parseJSON(){
        JsonArrayRequest jsonReq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i < response.length(); i++){
                        String title = response.getJSONObject(i).getString("title");
                        String year = response.getJSONObject(i).getString("year");
                        String director = response.getJSONObject(i).getString("director");
                        String image = response.getJSONObject(i).getString("image");
                        String desc = response.getJSONObject(i).getString("description");

                        movies.add(new Movies(title, year, director, image, desc));
                    }

                    adapter = new Adapter(RecyclerActivity.this, movies);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(RecyclerActivity.this);
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON", "Error: " + error.getMessage());
            }

        });
        // Add the request to the RequestQueue.
        queue.add(jsonReq);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, Detail.class);
        Movies clickedItem = movies.get(position);


        detailIntent.putExtra(TITLE, clickedItem.getTitle());
        detailIntent.putExtra(YEAR, clickedItem.getYear());
        if(clickedItem.getDirector() != null){
            detailIntent.putExtra(DIRECTOR, clickedItem.getDirector());
        }else {
            detailIntent.putExtra(DIRECTOR, "unknown");
        }

        detailIntent.putExtra(IMAGE, clickedItem.getImage());
        detailIntent.putExtra(DESCRIPTION, clickedItem.getDescription());

        startActivity(detailIntent);
    }

}
