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

import java.util.HashMap;
import java.util.Map;

public class odds extends AppCompatActivity {
    EditText editnp, editround, editpcr, editmc;
    Button localcomp, cloudcomp;
    TextView tvodds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odds);

        //initialize edit text fields
        editnp = (EditText) findViewById(R.id.editnp);
        editround = (EditText) findViewById(R.id.editround);
        editpcr = (EditText) findViewById(R.id.editpcr);
        editmc = (EditText) findViewById(R.id.editmc);

        //initialize buttons
        localcomp = (Button) findViewById(R.id.localcomp);
        cloudcomp = (Button) findViewById(R.id.cloudcomp);

        //initialize text view
        tvodds = (TextView) findViewById(R.id.tvodds);

        localcomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editnp.getText().toString().equals("")  || editnp.getText().toString().equals("0") || editround.getText().toString().equals("")  || editround.getText().toString().equals("0") || editmc.getText().toString().equals("")  || editmc.getText().toString().equals("0") || editpcr.getText().toString().equals("")  || editpcr.getText().toString().equals("0")){
                    Log.d("this", " happened");
                    Context context = getApplicationContext();
                     Toast t1 = Toast.makeText(context, "Invalid Entry! Please Enter Valid Data!", Toast.LENGTH_LONG);
                    t1.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0,0);
                    t1.show();
                }
                else {
                    tvodds.setText("");
                    int calcodds = ((Integer.parseInt(editnp.getText().toString()) * Integer.parseInt(editround.getText().toString()) * Integer.parseInt(editpcr.getText().toString()) * Integer.parseInt(editmc.getText().toString())) % 100);
                    String todisplay = "The odds of you winning the current hand are " + Integer.toString(calcodds) + "%";
                    tvodds.setText(todisplay);
                }
            }
        });
        cloudcomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editnp.getText().toString().equals("")  || editnp.getText().toString().equals("0") || editround.getText().toString().equals("")  || editround.getText().toString().equals("0") || editmc.getText().toString().equals("")  || editmc.getText().toString().equals("0") || editpcr.getText().toString().equals("")  || editpcr.getText().toString().equals("0")) {
                    Context context = getApplicationContext();
                    Toast t1 = Toast.makeText(context, "Invalid Entry! Please Enter Valid Data!", Toast.LENGTH_LONG);
                    t1.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0,0);
                    t1.show();
                }
                else {
                    postrequest("http://52.88.229.1:3000/cloudcompute");
                }

            }
        });
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
                        tvodds.setText("");
                        tvodds.setText("The odds of you winning the current hand are " + response + "%");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //text_message.setText("That didn't work!");
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("np",editnp.getText().toString());
                params.put("round", editround.getText().toString());
                params.put("pcr", editpcr.getText().toString());
                params.put("mc", editmc.getText().toString());
                //params.put("", )
                return params;

            }

        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
