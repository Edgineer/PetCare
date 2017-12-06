package com.example.matthewsanchez.petcare.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//retrofit stuff
import com.example.matthewsanchez.petcare.api.model.UserDoc;
import com.example.matthewsanchez.petcare.api.service.UserClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.util.Log;
import android.widget.Toast;

import com.example.matthewsanchez.petcare.R;

public class SignUp extends AppCompatActivity {

    public UserDoc someUser;

    public static final String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE2";
    public static final String EXTRA_MESSAGE3 = "com.example.myfirstapp.MESSAGE3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Get the Intent that started this activity
        Intent intent = getIntent();
        //String userName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        //TextView textView = findViewById(R.id.textView);
        //textView.setText(userName);
    }

    public void goToTheDisplay(View view) {
        Intent intent = new Intent(this, SampleText.class);

        // variable collecting
        EditText usernameEdit = (EditText) findViewById(R.id.usernameID);
        EditText passwordEdit = (EditText) findViewById(R.id.passwordID);
        String usernameVariable = usernameEdit.getText().toString();
        String passwordVariable = passwordEdit.getText().toString();

        // make retrofit object
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://serene-retreat-67526.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        // Make signup request (toast if successful)
        UserClient client_u = retrofit.create(UserClient.class);
        Call<UserDoc> call_u = client_u.signup(usernameVariable, passwordVariable);
        call_u.enqueue(new Callback<UserDoc>() {
            @Override
            public void onResponse(Call<UserDoc> call_u, Response<UserDoc> response) {
                if (response.isSuccessful()) {
                    someUser = response.body();
                    Log.d("TAG", "USERNAME IS: " + someUser.getUsername());
                    Log.d("TAG", "PASSWORD IS: " + someUser.getPassword());
                    Log.d("TAG", "OBJECTID IS: " + someUser.getObjectId());
                    Log.d("TAG", "PETIDS IS" + someUser.getPetIds());
                    Toast.makeText(SignUp.this, "Successful SignUp! Please return to the login page and login.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Log.d("TAG", "UNSUCCESSFUL, RETURN CODE: " + response.code() + " (username probably already exists)");
                    Toast.makeText(SignUp.this, "Username already exists. Please try again.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserDoc> call_u, Throwable t) {
                Toast.makeText(SignUp.this, "Error. Network connection :(", Toast.LENGTH_SHORT).show();
            }
        });


        //intent.putExtra(EXTRA_MESSAGE2, usernameVariable);
        //intent.putExtra(EXTRA_MESSAGE3, passwordVariable);

        //startActivity(intent);
    }
}
