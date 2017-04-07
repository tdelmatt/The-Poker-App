package com.example.tdelmatt.myapplication;

import android.content.Context;
import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    EditText edit_username, edit_password;
    TextView text_message;
    Button button_login, newacctbut;
    String username, playerstatus, dob, password;
    public int getstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getstatus = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_password = (EditText) findViewById(R.id.edit_password);
        text_message = (TextView) findViewById(R.id.textMessage);
        button_login = (Button) findViewById(R.id.button_login);
        newacctbut = (Button) findViewById(R.id.newacctbut);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //text_message.setText("username: " + edit_username.getText().toString() + "password: " + edit_password.getText().toString());
                //getrequest("http://52.88.229.1:3000/startpage");
                postrequest("http://52.88.229.1:3000/login");
            }
        });
        newacctbut.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent toaccts = new Intent(login.this, newprofile.class);
                startActivity(toaccts);
            }
        });
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
                text_message.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    void postrequest(String url){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //String url ="http://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //text_message.setText("Response is: "+ response);
                        String json = response.toString();
                        Log.d("strin is ", json);
                        Log.d("strin is ", json);
                        if(json.equals("error")){
                            Context context = getApplicationContext();
                            Toast t1 = Toast.makeText(context, "Invalid Username or Password. Try Again.", Toast.LENGTH_LONG);
                            t1.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL, 0,0);
                            t1.show();
                        }
                        else{
                            //text_message.setText(response);
                            try {

                                Log.d("this", "happened");
                                JSONObject object = (JSONObject) new JSONTokener(json).nextValue();
                                username = object.getString("username");
                                password = object.getString("password");
                                JSONArray casinos = object.getJSONArray("casinos");
                                dob = object.getString("dob");
                                playerstatus = object.getString("playerstatus");
                                Log.d("dob is ", dob);
                                Log.d("username is ", username);
                                getstatus = 1;

                            }catch (JSONException e){
                                getstatus = 0;
                                throw new RuntimeException(e);
                            }
                            if(getstatus == 1) {
                                Intent tomain = new Intent(login.this, first.class);
                                tomain.putExtra("username", username);
                                tomain.putExtra("password", password);
                                tomain.putExtra("dob", dob);
                                tomain.putExtra("playerstatus", playerstatus);
                                startActivity(tomain);
                            }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                text_message.setText("That didn't work!");
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", edit_username.getText().toString());
                params.put("password", edit_password.getText().toString());
                return params;
            }

        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    void getjsonstring(String url){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        text_message.setText("Response: " + response.toString());

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

// Access the RequestQueue through your singleton class.
        queue.add(jsObjRequest);
    }


}
