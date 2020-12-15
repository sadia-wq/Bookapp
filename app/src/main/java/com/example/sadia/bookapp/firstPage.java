package com.example.sadia.bookapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class firstPage extends AppCompatActivity {

    EditText editText;
    Button btn;
    String name,link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        final Context context=this;


        TextView booktextView = (TextView) findViewById(R.id.booktextView);
        editText=(EditText) findViewById(R.id.editText);
        btn= (Button)findViewById(R.id.btn);
        Firebase.setAndroidContext(this);


        booktextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2=new Intent(context,BookList.class);
                startActivity(intent2);


            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link=editText.getText().toString();

                if(link.equals("")){
                    editText.setError("can't be blank");
                }


                else{
                    final ProgressDialog pd = new ProgressDialog(firstPage.this);
                    pd.setMessage("sending...");
                    pd.show();

                    String url = "https://project-3-2-5368e-default-rtdb.firebaseio.com/links.json";

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            Firebase reference = new Firebase("https://project-3-2-5368e-default-rtdb.firebaseio.com/links");

                            if(s.equals("null")) {
                                reference.child((Objects.requireNonNull(FirebaseAuth.getInstance()
                                        .getCurrentUser()).getDisplayName())).push().setValue(link);
                                Toast.makeText(firstPage.this, "message sent", Toast.LENGTH_LONG).show();

                            }
                            else {
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    reference.child((Objects.requireNonNull(FirebaseAuth.getInstance()
                                            .getCurrentUser()).getDisplayName())).push().setValue(link);

                                    Toast.makeText(firstPage.this, "message sent", Toast.LENGTH_LONG).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();


                        }

                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError );
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(firstPage.this);
                    rQueue.add(request);
                }


                editText.setText("");
            }
        });
    }
}
