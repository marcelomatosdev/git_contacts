package com.example.gitcontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.MessageQueue;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.events.EventHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.BlockingDeque;


public class AddContactActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private WebView wbAddContact;
    private ImageButton btnSearch;
    private EditText etSearch;
    private Button btnAddNewContact;
    private String username;
    private RequestQueue mRequestQueue;
   // private SharedPreferences sharedPreferences;
   // private String current_user;
   //private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);


        username = "";
        wbAddContact = findViewById(R.id.wbAddContact);
        wbAddContact.setWebViewClient(new WebViewClient());
       // floatingActionButton = findViewById(R.id.fab);

        btnSearch = findViewById(R.id.btnSearch);
        etSearch = findViewById(R.id.etSearch);
        btnAddNewContact = findViewById(R.id.btnAddNewContact);

        toolbar = findViewById(R.id.AddContactMenu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add a contact");

        EventHandler eventHandler = new EventHandler();

        btnSearch.setOnClickListener(eventHandler);
        btnAddNewContact.setOnClickListener(eventHandler);
//
//        sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
//        current_user = sharedPreferences.getString("username","Missing Username");

    }


    class EventHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnSearch:
                    username = etSearch.getText().toString();
                    if (username != "") {
                        wbAddContact.loadUrl("https://github.com/" + username);
                        etSearch.clearFocus();
                        btnAddNewContact.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.btnAddNewContact:
                    if (username != "") {
                        addContact(username);

                    }
                    break;
            }
        }
    }

    public void addContact(String username){
        String mUsername = username;
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON(mUsername);

    }

    private void parseJSON(String username) {
        JSONObject object = new JSONObject();
        try {
            object.put("username",username);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://my-git-network.herokuapp.com/gitfriends";
        Log.d("Marcelo", "parseJSON:2 "+object);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Intent intentMain = new Intent(AddContactActivity.this, MainActivity.class);
                startActivity(intentMain);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intentMain = new Intent(AddContactActivity.this, MainActivity.class);
                startActivity(intentMain);
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }
}