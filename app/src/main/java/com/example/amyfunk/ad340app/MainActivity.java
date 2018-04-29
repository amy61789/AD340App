package com.example.amyfunk.ad340app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();


    String[] arr = {"Zombie", "doesn't work", "not this one", "null" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(new ButtonAdapter(this));

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
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

    class BtnOnClickListener implements View.OnClickListener
    {

        public void onClick(View v)
        {
            Log.d(TAG, "tapped button");
            int id = v.getId();
            Intent intent;
            switch (id) {
                case 0:
                    intent = new Intent(getBaseContext(), RecyclerActivity.class);
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