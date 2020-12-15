package com.example.sadia.bookapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class BookList extends AppCompatActivity {

    ListView bookList;
    ArrayList<String> al = new ArrayList<>();
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        bookList = (ListView)findViewById(R.id.bookList);

        pd = new ProgressDialog(BookList.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://project-3-2-5368e-default-rtdb.firebaseio.com/GENRE.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(BookList.this);
        rQueue.add(request);

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.BookName= al.get(position);
                startActivity(new Intent(BookList.this, home.class));
            }
        });
    }

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                al.add(key);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        bookList.setVisibility(View.VISIBLE);
        bookList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));

        pd.dismiss();
    }
}
