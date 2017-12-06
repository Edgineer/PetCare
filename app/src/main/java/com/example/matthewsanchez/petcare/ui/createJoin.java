package com.example.matthewsanchez.petcare.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class createJoin extends AppCompatActivity {

    UserDoc someUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_join);

        // Get the Intent that started this activity
        Intent intent = getIntent();
        someUser = (UserDoc)intent.getSerializableExtra("userJoinObject");
        //Toast.makeText(createJoin.this, someUser.getObjectId(),
        //        Toast.LENGTH_LONG).show();
    }

    public void returnToUserProfile2(View view) {
        // variable collecting
        EditText newPetID = (EditText) findViewById(R.id.Reoccuring);
        String petIDString = newPetID.getText().toString();

        //Toast.makeText(createJoin.this, someUser.getObjectId(),
        //        Toast.LENGTH_LONG).show();

        // make retrofit object
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://serene-retreat-67526.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        // Add *already existing* petId to someUser
        UserClient client_u = retrofit.create(UserClient.class);
        Call<UserDoc> call_u = client_u.addPet(someUser.getObjectId(), petIDString);
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

                    Intent intent = getIntent();
                    intent.putExtra("key2", someUser);
                    setResult(RESULT_OK, intent);
                    finish();

                } else {
                    Log.d("TAG", "UNSUCCESSFUL, RETURN CODE: " + response.code() + "(pet or user does not already exist");
                    Toast.makeText(createJoin.this, "Invalid/Nonexistent user id or pet id",
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<UserDoc> call_u, Throwable t) {
                Toast.makeText(createJoin.this, "error :(", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
