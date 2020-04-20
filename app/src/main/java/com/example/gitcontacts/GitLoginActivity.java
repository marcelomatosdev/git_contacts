package com.example.gitcontacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;
import com.squareup.picasso.Picasso;

public class GitLoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnLogin;
    public String current_user;
    public String current_username;
    private ImageView imgLogin;
    private ImageView imgGitContactsLogo;

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

        Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    // User is signed in.
                                    // IdP data available in
                                    // authResult.getAdditionalUserInfo().getProfile().
                                    // The OAuth access token can also be retrieved:
                                    // authResult.getCredential().getAccessToken().
                                    current_user = authResult.getUser().getDisplayName();
                                    current_username = authResult.getAdditionalUserInfo().getUsername();
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
                                    // User is signed in.
                                    // IdP data available in
                                    // authResult.getAdditionalUserInfo().getProfile().
                                    // The OAuth access token can also be retrieved:
                                    // authResult.getCredential().getAccessToken().
                                    current_user = authResult.getUser().getDisplayName();
                                    current_username = authResult.getAdditionalUserInfo().getUsername();
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

}