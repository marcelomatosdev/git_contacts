package com.example.gitcontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.events.EventHandler;


public class AddContactActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private WebView wbAddContact;
    private ImageButton btnSearch;
    private EditText etSearch;
    private Button btnAddNewContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        wbAddContact = findViewById(R.id.wbAddContact);
        wbAddContact.setWebViewClient(new WebViewClient());

        btnSearch = findViewById(R.id.btnSearch);
        etSearch = findViewById(R.id.etSearch);
        btnAddNewContact = findViewById(R.id.btnAddNewContact);

        toolbar = findViewById(R.id.AddContactMenu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add a contact");

        EventHandler eventHandler = new EventHandler();

        btnSearch.setOnClickListener(eventHandler);

    }


    class EventHandler implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnSearch:
                    String username = etSearch.getText().toString();
                    wbAddContact.loadUrl("https://github.com/"+username);
                    etSearch.clearFocus();
                    btnAddNewContact.setVisibility(View.VISIBLE);
                    break;

            }
        }
    }


}
