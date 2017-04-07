package com.example.tdelmatt.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class learn1 extends AppCompatActivity {
    protected TextView text1;
    protected Button but1;
    protected TextView text2;
    protected Button but2;
    protected TextView text3;
    protected Button but3;
    protected TextView text4;
    protected Button but4;
    protected TextView text5;
    protected Button but5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn1);

        text1 =(TextView)findViewById(R.id.text1);
        but1 = (Button) findViewById(R.id.but1);
        text2 = (TextView)findViewById(R.id.text2);
        but2 = (Button)findViewById(R.id.but2);
        text3 =(TextView) findViewById(R.id.text3);
        but3 =(Button) findViewById(R.id.but3);
        text4 =(TextView) findViewById(R.id.text4);
        but4 = (Button)findViewById(R.id.but4);
        text5 =(TextView) findViewById(R.id.text5);
        but5 = (Button)findViewById(R.id.but5);

        but1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(text1.getText() == "")
                    text1.setText("There are 4 betting rounds in texas holdem");
                else
                    text1.setText("");
            }
        });
        but2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(text2.getText() == "")
                    text2.setText("R1 2 personal, R2 3 communal R3 1 communal R4 1 communal");
                else
                    text2.setText("");
            }
        });
        but3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(text3.getText() == "")
                    text3.setText("About 10%");
                else
                    text3.setText("");
            }
        });
        but4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(text4.getText() == "")
                    text4.setText("About 2%");
                else
                    text4.setText("");
            }
        });
        but5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(text5.getText() == "")
                    text5.setText("About 10%");
                else
                    text5.setText("");
            }
        });
    }
}
