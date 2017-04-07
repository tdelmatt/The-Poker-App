package com.example.tdelmatt.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class newprofile extends AppCompatActivity {

    EditText editusername, editpassword, editdob;
    RadioGroup editpstatus;
    Button submit;
    RadioButton checkedbutton;
    protected String pstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newprofile);
        submit = (Button) findViewById(R.id.submit);

        editpstatus = (RadioGroup) findViewById(R.id.editpstatus);
        editusername = (EditText) findViewById(R.id.editusername);
        editpassword = (EditText) findViewById(R.id.editpassword);
        editdob = (EditText) findViewById(R.id.editdob);

        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                int checked = editpstatus.getCheckedRadioButtonId();
                RadioButton checkedbutton = (RadioButton) findViewById(checked);
                pstatus = checkedbutton.getText().toString();
                Log.d("radio text is", (String) checkedbutton.getText());
                postrequest("http://52.88.229.1:3000/newuserprofile");
            }
        });

        int checked = editpstatus.getCheckedRadioButtonId();
        checkedbutton = (RadioButton) findViewById(checked);
        //Log.d("radio text is", (String) checkedbutton.getText());
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
                        if(response.equals("success")){
                            Context context = getApplicationContext();
                            Toast t1 = Toast.makeText(context, "User Profile Created!", Toast.LENGTH_LONG);
                            t1.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL, 0,0);
                            t1.show();
                        }
                        else if(response.equals("error")){
                            Context context = getApplicationContext();
                            Toast t1 = Toast.makeText(context, "Could Not Create Profile! Try different Username.", Toast.LENGTH_LONG);
                            t1.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL, 0,0);
                            t1.show();
                        }
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
                params.put("username", editusername.getText().toString());
                params.put("password", editpassword.getText().toString());
                params.put("dob", editdob.getText().toString());
                params.put("playerstatus", pstatus);
                return params;

            }

        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
