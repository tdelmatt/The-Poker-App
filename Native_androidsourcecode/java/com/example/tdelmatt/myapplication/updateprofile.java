package com.example.tdelmatt.myapplication;

import android.content.Context;
import android.provider.MediaStore;
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

public class updateprofile extends AppCompatActivity {

    protected String username, password, dob, playerstatus;
    protected EditText editpassword, editdob;
    protected RadioGroup editpstatus;
    protected Button updatebut, deletebut;
    protected String playerstatus1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);

        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        playerstatus = getIntent().getStringExtra("playerstatus");
        dob = getIntent().getStringExtra("dob");
        editpstatus = (RadioGroup) findViewById(R.id.editpstatus1);
        updatebut = (Button) findViewById(R.id.updatebutton);
        deletebut = (Button) findViewById(R.id.deletebutton);


        editpassword = (EditText) findViewById(R.id.editpassword1);
        editdob = (EditText) findViewById(R.id.editdob1);

        final RadioButton Pro = (RadioButton) findViewById(R.id.Pro1);
        final RadioButton Recreational = (RadioButton) findViewById(R.id.Recreational1);
        final RadioButton Amateur = (RadioButton) findViewById(R.id.Amateur1);
        final RadioButton Beginner = (RadioButton) findViewById(R.id.Beginner1);

        Log.d("playerstatus is ", playerstatus);

        if(playerstatus.equals("Pro")){
            Pro.setChecked(true);
        }
        else if(playerstatus.equals("Recreational")){
            Recreational.setChecked(true);
        }
        else if(playerstatus.equals("Amateur")){
            Amateur.setChecked(true);
        }
        else if(playerstatus.equals("Beginner")){
            Beginner.setChecked(true);
        }

        editpassword.setText(password);
        editdob.setText(dob);

        updatebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checked = editpstatus.getCheckedRadioButtonId();
                RadioButton checkedbutton = (RadioButton) findViewById(checked);
                putrequest("http://52.88.229.1:3000/updateprofile");
                playerstatus1 = checkedbutton.getText().toString();
                Log.d("radio text is", (String) checkedbutton.getText());
                Context context = getApplicationContext();
                Toast t1 = Toast.makeText(context, "Profile Updated!", Toast.LENGTH_LONG);
                t1.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL, 0,0);
                t1.show();
            }
        });
        deletebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url1 = "http://52.88.229.1:3000/deleteprofile?username=" + username;
                deleterequest(url1);
                Context context = getApplicationContext();
                Toast t1 = Toast.makeText(context, "Profile Deleted!", Toast.LENGTH_LONG);
                t1.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL, 0,0);
                t1.show();
            }
        });
    }

    void putrequest(String url){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //String url ="http://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
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
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", editpassword.getText().toString());
                params.put("dob", editdob.getText().toString());
                params.put("playerstatus", playerstatus1);
                return params;

            }

        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    void deleterequest(String url) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //String url ="http://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
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
}
