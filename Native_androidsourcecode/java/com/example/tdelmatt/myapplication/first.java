package com.example.tdelmatt.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class first extends AppCompatActivity {

    public Button but1;
    public Button oddsbutton;
    public Button casinobutton,mycasinos, updateprofile;
    public TextView tvwelcome;
    protected String username, password, dob, playerstatus;

    public void init(){
        but1=(Button)findViewById(R.id.but1);
        oddsbutton=(Button)findViewById(R.id.oddsbutton);
        casinobutton=(Button)findViewById(R.id.casinobutton);
        mycasinos=(Button)findViewById(R.id.mycasinos);
        updateprofile=(Button)findViewById(R.id.updateprofile);

        mycasinos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(first.this,mycasinos.class);
                toy.putExtra("username", username);
                toy.putExtra("password", password);
                toy.putExtra("dob", dob);
                toy.putExtra("playerstatus", playerstatus);
                startActivity(toy);
            }
        });
        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(first.this,updateprofile.class);
                toy.putExtra("username", username);
                toy.putExtra("password", password);
                toy.putExtra("dob", dob);
                toy.putExtra("playerstatus", playerstatus);
                startActivity(toy);
            }
        });
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(first.this,learn1.class);
                startActivity(toy);
            }
        });
        oddsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent toodds = new Intent(first.this,odds.class);
                startActivity(toodds);
            }
        });
        casinobutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent tocasino = new Intent(first.this,findcasino.class);
                startActivity(tocasino);
            }
        });

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        tvwelcome = (TextView) findViewById(R.id.textView11);
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        playerstatus = getIntent().getStringExtra("playerstatus");
        dob = getIntent().getStringExtra("dob");
        Log.d("this is ", dob);
        String welcome = "Welcome Back " + username + "!";
        tvwelcome.setText(welcome);
        init();

    }
}
