package com.example.matthewsanchez.petcareproject.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matthewsanchez.petcareproject.R;
import com.example.matthewsanchez.petcareproject.api.model.UserDoc;
import com.example.matthewsanchez.petcareproject.api.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class createGroup extends AppCompatActivity {

    UserDoc someUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        // Get the Intent that started this activity
        Intent intent = getIntent();
        someUser = (UserDoc)intent.getSerializableExtra("userCreateObject");

    }

    public void returnToUserProfile(View view) {
        //Intent intent = new Intent(this, SignUp.class);
        //startActivity(intent);

        // variable collecting
        EditText newPetName = (EditText) findViewById(R.id.Reoccuring);
        String petNameString = newPetName.getText().toString();

        // make retrofit object
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://serene-retreat-67526.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        // Add petNameString to someUser
        UserClient client_u = retrofit.create(UserClient.class);
        Call<UserDoc> call_u = client_u.createPet(someUser.getObjectId(), petNameString);
        call_u.enqueue(new Callback<UserDoc>() {
            @Override
            public void onResponse(Call<UserDoc> call_u, Response<UserDoc> response) {
                if (response.isSuccessful()) {
                    someUser = response.body();
                    Log.d("TAG", "USERNAME IS: " + someUser.getUsername());
                    Log.d("TAG", "PASSWORD IS: " + someUser.getPassword());
                    Log.d("TAG", "OBJECTID IS: " + someUser.getObjectId());
                    Log.d("TAG", "PETIDS IS" + someUser.getPetIds());
                    Log.d("TAG", "PETIDS IS" + someUser.getpetNames());


                    //finish();
                    //intent.putExtra("userObject", someUser);
                    //startActivity(intent);

                    Intent intent = getIntent();
                    intent.putExtra("key", someUser);
                    setResult(RESULT_OK, intent);
                    finish();

                    //Toast.makeText(createGroup.this, "Successful Pet Creation! Return to your user profile page!",
                    //        Toast.LENGTH_LONG).show();
                } else {
                    Log.d("TAG", "UNSUCCESSFUL, RETURN CODE: " + response.code() + " (exists)");
                    Toast.makeText(createGroup.this, "Invalid/Nonexistent user id",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserDoc> call_u, Throwable t) {
                Toast.makeText(createGroup.this, "Error. Network connection :(", Toast.LENGTH_SHORT).show();
            }
        });


        //finish();
    }
}
