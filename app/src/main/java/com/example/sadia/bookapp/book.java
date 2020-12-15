package com.example.sadia.bookapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class book extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);



        String url = "https://project-3-2-5368e-default-rtdb.firebaseio.com/GENRE.json";


        final ProgressDialog pd = new ProgressDialog(book.this);
        pd.setMessage("Loading...");
        pd.show();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject obj = new JSONObject(s);

                    String bookUrl = obj.getJSONObject(UserDetails.BookName).getString(UserDetails.bookRef);


                    WebView webview=(WebView)findViewById(R.id.webView);
                    ImageButton imageBtn = (ImageButton)findViewById(R.id.imageBtn);

                    webview.getSettings().setJavaScriptEnabled(true);

                    webview.loadUrl(bookUrl);


                    imageBtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            startActivity(new Intent(book.this, openMessage.class));

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                pd.dismiss();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
                pd.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(book.this);
        rQueue.add(request);

    }
}
