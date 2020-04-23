package com.example.gitcontacts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
import static com.example.gitcontacts.MainActivity.EXTRA_GIT_ID;

public class DetailContactActivity extends AppCompatActivity {

    private Toolbar toolbar;
    public int id;
    public int gitId;
    private RequestQueue mRequestQueue;
    private ImageButton btnGithubPage;
    private SharedPreferences sharedP;
    private SharedPreferences sharedPreferences;

    private ImageView imageView;
    private TextView textViewUsername;
    private TextView textViewName;
    private TextView textViewBio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        sharedP = getSharedPreferences("sharedP", Context.MODE_PRIVATE);



        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL);
        String username = intent.getStringExtra(EXTRA_USERNAME);
        String name = intent.getStringExtra(EXTRA_NAME);
        String bio = intent.getStringExtra(EXTRA_BIO);
        int repositories = intent.getIntExtra(EXTRA_REPOSITORIES, 0);
        int followers = intent.getIntExtra(EXTRA_FOLLOWERS, 0);
        gitId = intent.getIntExtra(EXTRA_GIT_ID, 0);
        id = intent.getIntExtra(EXTRA_ID, 0);
        btnGithubPage = findViewById(R.id.btnGithubPage);
        EventHandler eventHandler = new EventHandler();
        btnGithubPage.setOnClickListener(eventHandler);


        toolbar = findViewById(R.id.DetailContactMenu);
        setSupportActionBar(toolbar);

        String showName = name;
        String showBio = '"'+bio+'"';

        if(name.contentEquals("null")){
            showName = username;
        }

        if(bio.contentEquals("null")){
            showBio = "";
        }


        getSupportActionBar().setTitle(showName);

        imageView = findViewById(R.id.image_view_detail);
        textViewUsername = findViewById(R.id.text_view_username_detail);
        textViewName = findViewById(R.id.text_view_name_detail);
        textViewBio = findViewById(R.id.text_view_bio_detail);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        textViewUsername.setText(username);
        textViewName.setText("Name: " + showName);
        textViewBio.setText(showBio);

        SharedPreferences.Editor editor = sharedP.edit();
        editor.putString("usernamepage", username);
        editor.commit();


        settings();
    }


    class EventHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnGithubPage:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Log.d("Marcelo", "onClick: "+AppCompatDelegate.getDefaultNightMode());
                    Intent intentGithubPage = new Intent(DetailContactActivity.this, GithubPageActivity.class);
                    intentGithubPage.putExtra("username", EXTRA_USERNAME);
                    startActivity(intentGithubPage);
                    break;
                    }


            }
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
                Log.d("Marcelo", "onOptionsItemSelected: "+gitId);
                removeContact(gitId);
                return true;
            case R.id.settings:
                Intent intentMain = new Intent(DetailContactActivity.this, SettingsActivity.class);
                startActivity(intentMain);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void removeContact(int selectedId){
        int mGitId = selectedId;
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON(mGitId);
    }

    private void parseJSON(int mGitId) {
        String url = "https://my-git-network.herokuapp.com/gitfriends/"+mGitId;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.DELETE,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Intent intentMain = new Intent(DetailContactActivity.this, MainActivity.class);
                startActivity(intentMain);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intentMain = new Intent(DetailContactActivity.this, MainActivity.class);
                startActivity(intentMain);
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        settings();

    }

    public void settings(){
        //Use for settings
        sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        String fontSize = sharedPreferences.getString("fontSize","20");
        String fontColor = sharedPreferences.getString("fontColor","BLACK");
        String backgroundColor = sharedPreferences.getString("backgroundColor","#FAFAFA");
        int fontStyle = sharedPreferences.getInt("fontStyle",2000213);

        textViewUsername.setTextSize(Integer.parseInt((fontSize)));
        textViewUsername.setTextColor(Color.parseColor(fontColor));
        textViewUsername.setBackgroundColor(Color.parseColor(backgroundColor));
        textViewUsername.setTextAppearance(getApplicationContext(), fontStyle);

        textViewName.setTextSize(Integer.parseInt((fontSize)));
        textViewName.setTextColor(Color.parseColor(fontColor));
        textViewName.setBackgroundColor(Color.parseColor(backgroundColor));
        textViewName.setTextAppearance(getApplicationContext(), fontStyle);

        textViewBio.setTextSize(Integer.parseInt((fontSize)));
        textViewBio.setTextColor(Color.parseColor(fontColor));
        textViewBio.setBackgroundColor(Color.parseColor(backgroundColor));
        //textViewBio.setTextAppearance(getApplicationContext(), fontStyle);


        //--
    }



}


