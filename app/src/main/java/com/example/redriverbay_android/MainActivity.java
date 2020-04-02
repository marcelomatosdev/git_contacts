package com.example.redriverbay_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ProductCardAdapter.OnItemClickListener {

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_LIKES = "likeCount";

    private RecyclerView mRecyclerView;
    private ProductCardAdapter mProductCardAdapter;
    private ArrayList<ProductItem> mProductList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProductList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

    }

    private void parseJSON() {
        String url = "https://api.github.com/users?per_page=5";
       // String url = "https://api.github.com/users";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.d("Marcelo", response.toString());
                            //JSONArray jsonArray = new JSONArray(response);
                            JSONArray jsonArray = response.getJSONArray(" ");
                            //JSONArray jsonArray = response.getJSONArray("");
                            //JSONArray jsonArray = new JSONArray(response);
                            Log.d("Marcelo", "onResponse: ");
                          //  Log.d("Marcelo", jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject product = jsonArray.getJSONObject(i);

//                                String creatorName = product.getString("user");
//                                String imageUrl = product.getString("webformatURL");
//                                int likeCount = product.getInt("likes");
//
                                String creatorName = product.getString("login");
                                String imageUrl = product.getString("avatar_url");
                                int likeCount = product.getInt("id");

                                mProductList.add(new ProductItem(imageUrl, creatorName, likeCount));
                            }

                            mProductCardAdapter = new ProductCardAdapter(MainActivity.this, mProductList);
                            mRecyclerView.setAdapter(mProductCardAdapter);
                            mProductCardAdapter.setOnItemClickListener(MainActivity.this);

                        } catch (JSONException e) {
                            Log.d("Marcelo", "onResponse0: ");

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Log.d("Marcelo", "parseJSON: ");
        mRequestQueue.add(request);
        Log.d("Marcelo", request.toString());

    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this,DetailProductActivity.class);
        ProductItem clickedItem = mProductList.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
        detailIntent.putExtra(EXTRA_LIKES, clickedItem.getLikes());
        startActivity(detailIntent);
    }
}
