package com.example.gitcontacts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import static com.example.gitcontacts.MainActivity.EXTRA_BIO;
import static com.example.gitcontacts.MainActivity.EXTRA_FOLLOWERS;
import static com.example.gitcontacts.MainActivity.EXTRA_IMAGE_URL;
import static com.example.gitcontacts.MainActivity.EXTRA_NAME;
import static com.example.gitcontacts.MainActivity.EXTRA_REPOSITORIES;
import static com.example.gitcontacts.MainActivity.EXTRA_USERNAME;
import static com.example.gitcontacts.MainActivity.EXTRA_ID;

public class DetailContactActivity extends AppCompatActivity {

    private Toolbar toolbar;
    public int id;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);



        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL);
        String username = intent.getStringExtra(EXTRA_USERNAME);
        String name = intent.getStringExtra(EXTRA_NAME);
        String bio = intent.getStringExtra(EXTRA_BIO);
        int repositories = intent.getIntExtra(EXTRA_REPOSITORIES, 0);
        int followers = intent.getIntExtra(EXTRA_FOLLOWERS, 0);

        id = intent.getIntExtra(EXTRA_ID, 0);

        toolbar = findViewById(R.id.DetailContactMenu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewUsername = findViewById(R.id.text_view_username_detail);
        TextView textViewName = findViewById(R.id.text_view_name_detail);
        TextView textViewBio = findViewById(R.id.text_view_bio_detail);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        textViewUsername.setText(username);
        textViewName.setText("Name: " + name);
        textViewBio.setText('"'+bio+'"');
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubar_detail, menu );
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.delete:
                Log.d("Marcelo", "onOptionsItemSelected: ");
                Log.d("Marcelo", ""+id);
                removeContact(id);

//                Intent intentRefresh = new Intent(RssFeed.this, RssFeed.class);
//                intentRefresh.putExtra("url", selectedURL);
//                finish();
//                startActivity(intentRefresh);
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void removeContact(int selectedId){
        int mId = selectedId;


        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON(mId);

    }

    private void parseJSON(int mId) {

        String url = "https://my-git-network.herokuapp.com/contacts/"+mId;
        Log.d("Marcelo", "Delete");
        Log.d("Marcelo", url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.DELETE,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Intent intentMain = new Intent(DetailContactActivity.this, MainActivity.class);
                startActivity(intentMain);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }





}


