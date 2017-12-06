package com.example.matthewsanchez.petcare.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matthewsanchez.petcare.R;
import com.example.matthewsanchez.petcare.api.model.UserDoc;
import com.example.matthewsanchez.petcare.api.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public UserDoc someUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /** Called when the user taps the Send button */

    public void goToSignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void goToPetProfileList(View view) {
        final Intent intent = new Intent(this, PetProfileList.class);

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

        // Make login request (toast if successful)
        UserClient client_u = retrofit.create(UserClient.class);
        Call<UserDoc> call_u = client_u.login(usernameVariable, passwordVariable);
        call_u.enqueue(new Callback<UserDoc>() {
            @Override
            public void onResponse(Call<UserDoc> call_u, Response<UserDoc> response) {
                if (response.isSuccessful()) {
                    someUser = response.body();
                    Log.d("TAG", "USERNAME IS: " + someUser.getUsername());
                    Log.d("TAG", "PASSWORD IS: " + someUser.getPassword());
                    Log.d("TAG", "OBJECTID IS: " + someUser.getObjectId());
                    Log.d("TAG", "PETIDS IS" + someUser.getPetIds());
                    intent.putExtra("userObject", someUser);
                    startActivity(intent);
                    //Toast.makeText(MainActivity.this, "Successful login! Please wait to be taken to user profile...",
                    //        Toast.LENGTH_LONG).show();
                } else {
                    Log.d("TAG", "UNSUCCESSFUL, RETURN CODE: " + response.code() + " (exists)");
                    Toast.makeText(MainActivity.this, "Bad login. Please try a different username/password combination.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserDoc> call_u, Throwable t) {
                Toast.makeText(MainActivity.this, "Error. Network connection :(", Toast.LENGTH_SHORT).show();
            }
        });

        //intent.putExtra("userObject", someUser);

        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);

    }

    //List<String> myPetIds;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
