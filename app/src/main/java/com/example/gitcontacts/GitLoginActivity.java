package com.example.gitcontacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class GitLoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnLogin;
    public String current_user;
    public String current_username;
    private ImageView imgLogin;
    private ImageView imgGitContactsLogo;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imgLogin = findViewById(R.id.imgGitLogin);
        imgGitContactsLogo = findViewById(R.id.imgGitContactsLogo);
        imgLogin.setImageResource(R.mipmap.ic_github_login_round);
        imgGitContactsLogo.setImageResource(R.mipmap.ic_git_contacts_logo);


        btnLogin = findViewById(R.id.btnLogin);
        GitLoginActivity.EventHandler eventHandler = new GitLoginActivity.EventHandler();
        btnLogin.setOnClickListener(eventHandler);



    }


    class EventHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnLogin:
                    login();
                    break;
            }
        }
    }



    public void login()
    {
        //Github login auth
        mAuth = FirebaseAuth.getInstance();

        OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");

        final Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    current_user = authResult.getUser().getDisplayName();
                                    current_username = authResult.getAdditionalUserInfo().getUsername();

                                    mRequestQueue = Volley.newRequestQueue(getApplicationContext());
                                    parseJSON(current_username);

                                    Intent intentMain = new Intent(GitLoginActivity.this, MainActivity.class);
                                    intentMain.putExtra("name", current_user);
                                    intentMain.putExtra("username", current_username);
                                    startActivity(intentMain);

                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                }
                            });
        } else {
            mAuth
                    .startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    current_user = authResult.getUser().getDisplayName();
                                    current_username = authResult.getAdditionalUserInfo().getUsername();

                                    mRequestQueue = Volley.newRequestQueue(getApplicationContext());
                                    parseJSON(current_username);

                                    Intent intentMain = new Intent(GitLoginActivity.this, MainActivity.class);
                                    intentMain.putExtra("name", current_user);
                                    intentMain.putExtra("username", current_username);
                                    startActivity(intentMain);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                }
                            });
        }
        //Login Github auth - END
    }
    private void parseJSON(String current_username) {
        JSONObject object = new JSONObject();
        try {
            object.put("username",current_username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://my-git-network.herokuapp.com/users";
        //String url = "http://localhost:3000/users";

        Log.d("Marcelo", "username "+object);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Marcelo", "suc");
                Intent intentMain = new Intent(GitLoginActivity.this, MainActivity.class);
               startActivity(intentMain);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Marcelo", "err");
                Intent intentMain = new Intent(GitLoginActivity.this, MainActivity.class);
                startActivity(intentMain);
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

}