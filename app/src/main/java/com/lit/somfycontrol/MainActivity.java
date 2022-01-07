package com.lit.somfycontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn1.setOnClickListener(view ->
        {
            Sender.senden('U');
        });
        btn2.setOnClickListener(view ->
        {
            Sender.senden('M');
        });
        btn3.setOnClickListener(view ->
        {
            Sender.senden('D');
        });
    }
}