package com.example.amyfunk.ad340app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cameras extends AppCompatActivity {

    Context context;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recyclerViewLM;

    String url = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";

    List<TrafficCam> cameraList = new ArrayList<>();

    Map<String, String> baseUrls = new HashMap();

    {
        {
            baseUrls.put("sdot", "http://www.seattle.gov/trafficcams/images/");
            baseUrls.put("wsdot", "http://images.wsdot.wa.gov/nw/");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        context = getApplicationContext();
        recyclerView = findViewById(R.id.recyclerview1);
        recyclerViewLM = new LinearLayoutManager(context);

        recyclerViewLM = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLM);

        recyclerViewAdapter = new Cameras.CustomAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
        RequestQueue q = Volley.newRequestQueue(this);
        JsonObjectRequest jsonReq = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Cameras", response.toString());

                        try {
                            JSONArray feature = response.getJSONArray("Features");
                            for (int i = 1; i < feature.length(); i++) {
                                JSONObject point = feature.getJSONObject(i);
                                JSONArray pointCoords = point.getJSONArray("PointCoordinate");
                                double[] coords = {pointCoords.getDouble(0), pointCoords.getDouble(1)};

                                JSONArray cameras = point.getJSONArray("Cameras");
                                for (int k = 0; k < cameras.length(); k++) {
                                    JSONObject camera = cameras.getJSONObject(k);
                                    TrafficCam cam = new TrafficCam(
                                            camera.getString("Description"),
                                            camera.getString("ImageUrl"),
                                            camera.getString("Type"),
                                            coords
                                    );
                                    cameraList.add(cam);
                                }
                            }

                            recyclerViewAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSON", "Error: " + error.getMessage());
                    }
                });

        q.add(jsonReq);
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView cName;
            public ImageView cImage;

            public ViewHolder(View v) {
                super(v);
                cName = v.findViewById(R.id.description);
                cImage = v.findViewById(R.id.image);
            }
        }

        @Override
        public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View item = getLayoutInflater().inflate(R.layout.activity_cameras, parent, false);
            ViewHolder vh = new ViewHolder(item);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.cName.setText(cameraList.get(position).getDescription());
            String url = baseUrls.get(cameraList.get(position).getType()) + cameraList.get(position).imageUrl();
            Picasso.get().load(url).into(holder.cImage);
        }

        @Override
        public int getItemCount() {
            return cameraList.size();
        }
    }
}
