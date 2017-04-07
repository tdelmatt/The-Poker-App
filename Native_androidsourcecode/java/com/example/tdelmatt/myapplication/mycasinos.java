package com.example.tdelmatt.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class mycasinos extends AppCompatActivity {


    protected String username, password, dob, playerstatus, parturl, json, casino;
    protected TextView casview1, casview2, casview3, casview4, casview5;
    protected Button addcasino, removecasino;
    protected EditText editTextaddcasino, editTextremovecasino;
    protected int track, i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycasinos);

        addcasino = (Button) findViewById(R.id.addcasino);
        removecasino = (Button) findViewById(R.id.removecasino);

        editTextaddcasino = (EditText) findViewById(R.id.editTextaddcasino);
        editTextremovecasino = (EditText) findViewById(R.id.editTextremovecasino);

        casview1 = (TextView) findViewById(R.id.casview1);
        casview2 = (TextView) findViewById(R.id.casview2);
        casview3 = (TextView) findViewById(R.id.casview3);
        casview4 = (TextView) findViewById(R.id.casview4);
        casview5 = (TextView) findViewById(R.id.casview5);

        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        playerstatus = getIntent().getStringExtra("playerstatus");
        dob = getIntent().getStringExtra("dob");
        parturl = "http://52.88.229.1:3000/getcasinoslist?username=" + username + "&password=" + password;
        getcasinos(parturl);

        addcasino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    casino = URLEncoder.encode(editTextaddcasino.getText().toString(), "UTF-8");
                }catch(UnsupportedEncodingException e){

                }
                Log.d("casino is ", casino);
                if(!casino.equals("")) {
                    parturl = "http://52.88.229.1:3000/addcasino?username=" + username + "&password=" + password + "&casino=" + casino;
                    getaddcasino(parturl);
                }
            }
        });

        removecasino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("edit text is ", editTextremovecasino.getText().toString());
                try {
                    casino = URLEncoder.encode(editTextremovecasino.getText().toString(), "UTF-8");
                }catch(UnsupportedEncodingException e){

                }
                parturl = "http://52.88.229.1:3000/removecasino?username=" + username + "&password=" + password + "&casino=" + casino;
                Log.d("part url: ", parturl);
                getremovecasino(parturl);
            }
        });

    }
    void getcasinos(String url){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //String url ="http://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //text_message.setText("Response is: "+ response);
                        Log.d("get all cainos string is ", response);
                        json = response.toString();
                        if(!json.equals("error")) {
                            try {
                                JSONObject object = (JSONObject) new JSONTokener(json).nextValue();
                                JSONArray casinoarr = object.getJSONArray("casinos");
                                for (i = 0; i < casinoarr.length(); i++) {
                                    track = i;
                                    if (i == 0) {
                                        Log.d("get all cainos 1 string is", casinoarr.getString(i));
                                        casview1.setText(casinoarr.getString(i));
                                    } else if (i == 1) {
                                        casview2.setText(casinoarr.getString(i));
                                    } else if (i == 2) {
                                        casview3.setText(casinoarr.getString(i));
                                    } else if (i == 3) {
                                        casview4.setText(casinoarr.getString(i));
                                    } else if (i == 4) {
                                        casview5.setText(casinoarr.getString(i));
                                    }
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //text_message.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    void getremovecasino(String url){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //String url ="http://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //text_message.setText("Response is: "+ response);
                        if(response.equals("error")){
                            Context context = getApplicationContext();
                            Toast t1 = Toast.makeText(context, "Invalid Entry!.", Toast.LENGTH_LONG);
                            t1.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL, 0,0);
                            t1.show();
                        }
                        else{
                            casview1.setText("");
                            casview2.setText("");
                            casview3.setText("");
                            casview4.setText("");
                            casview5.setText("");
                            parturl = "http://52.88.229.1:3000/getcasinoslist?username=" + username + "&password=" + password;
                            getcasinos(parturl);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //text_message.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    void getrequest(String url){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //String url ="http://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //text_message.setText("Response is: "+ response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //text_message.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    void getaddcasino(String url){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //String url ="http://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        if(!response.equals("error")) {
                            track = track + 1;
                            if(casview1.getText().toString().equals("")) {
                                track = 0;
                            }
                                Log.d("add casino string is", response);
                                if (track == 0) {
                                    casview1.setText(response);
                                } else if (track == 1) {
                                    casview2.setText(response);
                                } else if (track == 2) {
                                    casview3.setText(response);
                                } else if (track == 3) {
                                    casview4.setText(response);
                                } else if (track == 4) {
                                    casview5.setText(response);
                                }

                        }
                        else{
                            Context context = getApplicationContext();
                            Toast t1 = Toast.makeText(context, "Invalid Entry. Try Again.", Toast.LENGTH_LONG);
                            t1.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL, 0,0);
                            t1.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //text_message.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
