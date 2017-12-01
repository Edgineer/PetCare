package com.example.matthewsanchez.petcareproject.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.matthewsanchez.petcareproject.R;

public class SampleText extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_text);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message1 = intent.getStringExtra(SignUp.EXTRA_MESSAGE2);
        String message2 = intent.getStringExtra(SignUp.EXTRA_MESSAGE3);

        // Capture the layout's TextView and set the string as its text
        TextView textView1 = findViewById(R.id.usernameDisplay);
        textView1.setText(message1);
        TextView textView2 = findViewById(R.id.passwordDisplay);
        textView2.setText(message2);

    }
}
