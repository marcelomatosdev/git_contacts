package com.example.gitcontacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.CookieManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ContactCardAdapter.OnItemClickListener {

    public static final String EXTRA_IMAGE_URL = "imageUrl";
    public static final String EXTRA_USERNAME = "username";
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_BIO = "bio";
    public static final String EXTRA_REPOSITORIES = "repositories";
    public static final String EXTRA_FOLLOWERS = "followers";
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_GIT_ID = "git_id";

    private Toolbar toolbar;

    private RecyclerView mRecyclerView;
    private ContactCardAdapter mContactCardAdapter;
    private ArrayList<ContactItem> mContactList;
    private RequestQueue mRequestQueue;

    private String current_name;
    private String current_username;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Intent login = getIntent();
        current_name = login.getStringExtra("name");
        current_username = login.getStringExtra("username");
        sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);

        Log.d("Marcelo", "onCreate1: "+current_username);
        Log.d("Marcelo", "onCreate1: "+current_name);

        if (current_username != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", current_name);
            editor.putString("username", current_username);
            editor.commit();
        }else
        {
            current_name = sharedPreferences.getString("name", "");
            current_username = sharedPreferences.getString("username","Missing Username");
        }


        toolbar = findViewById(R.id.MainMenu);
        setSupportActionBar(toolbar);

        String showWelcome = current_name;
        if(current_name==null){
            showWelcome = current_username;
        }
        Log.d("Marcelo", "onCreate2: "+showWelcome);
        getSupportActionBar().setTitle("Welcome " + current_username);

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mContactList = new ArrayList<>();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubar_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.addContact_menuItem:
                Intent intentRefresh = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(intentRefresh);
                return true;
            case R.id.signOut_menuItem:
                sharedPreferences.edit().clear().commit();
                CookieManager cm = CookieManager.getInstance();
                cm.flush();
                FirebaseAuth.getInstance().signOut();

                Intent intentSignOut = new Intent(MainActivity.this, GitLoginActivity.class);
                startActivity(intentSignOut);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void parseJSON() {

        String url = "https://my-git-network.herokuapp.com/contacts";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject contact = response.getJSONObject(i);

                                int id = contact.getInt("id");
                                int git_id = contact.getInt("git_id");
                                String imageUrl = contact.getString("avatar_url");
                                String username = contact.getString("login");
                                String name = contact.getString("name");
                                String bio = contact.getString("bio");
                                //int repositories = contact.getInt("repositories");
                                //int followers = contact.getInt("followers");
                                int repositories = 10;
                                int followers = 20;
                                mContactList.add(new ContactItem(imageUrl, username,name,bio,repositories,followers, id, git_id));
                            }
                            mContactCardAdapter = new ContactCardAdapter(MainActivity.this, mContactList);
                            mRecyclerView.setAdapter(mContactCardAdapter);
                            mContactCardAdapter.setOnItemClickListener(MainActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailContactActivity.class);
        ContactItem clickedItem = mContactList.get(position);

        detailIntent.putExtra(EXTRA_IMAGE_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_USERNAME, clickedItem.getUsername());
        detailIntent.putExtra(EXTRA_NAME, clickedItem.getName());
        detailIntent.putExtra(EXTRA_BIO, clickedItem.getBio());
        detailIntent.putExtra(EXTRA_REPOSITORIES, clickedItem.getRepositories());
        detailIntent.putExtra(EXTRA_FOLLOWERS, clickedItem.getFollowers());
        detailIntent.putExtra(EXTRA_ID,clickedItem.getId());
        detailIntent.putExtra(EXTRA_GIT_ID,clickedItem.getGitId());
        startActivity(detailIntent);
    }
}
