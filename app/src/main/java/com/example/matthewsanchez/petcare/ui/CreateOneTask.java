package com.example.matthewsanchez.petcare.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matthewsanchez.petcare.R;
import com.example.matthewsanchez.petcare.api.model.PetDoc;
import com.example.matthewsanchez.petcare.api.service.PetClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateOneTask extends AppCompatActivity {

    PetDoc somePet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_one_task);

        Intent intent = getIntent();
        somePet = (PetDoc)intent.getSerializableExtra("petOneTaskObject");
    }

    public void returnToPetProfile2(View view) {
        // variable collecting
        EditText oneTask = (EditText) findViewById(R.id.Reoccuring);
        String singleTask = oneTask.getText().toString();

        // make retrofit object
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://serene-retreat-67526.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        // Create recurring task for this pet; add to db
        PetClient client_p = retrofit.create(PetClient.class);
        Call<PetDoc> call_p = client_p.createTask(somePet.getObjectId(), singleTask);
        call_p.enqueue(new Callback<PetDoc>() {
            @Override
            public void onResponse(Call<PetDoc> call_p, Response<PetDoc> response) {
                if (response.isSuccessful()) {
                    somePet = response.body();
                    Log.d("TAG", "PETNAME IS: " + somePet.getName());
                    Log.d("TAG", "PETID IS: " + somePet.getObjectId());
                    Log.d("TAG", "TASKS ARE IS" + somePet.getTaskNames());
                    Log.d("TAG", "RETASKS ARE IS" + somePet.getRetaskNames());

                    //taskIntent.putExtra("petObject", somePet);
                    //startActivity(taskIntent);

                    Intent intent = getIntent();
                    intent.putExtra("key4", somePet);
                    setResult(RESULT_OK, intent);
                    finish();

                } else {
                    Log.d("TAG", "UNSUCCESSFUL, RETURN CODE: " + response.code() + " (invalid PetID)");
                    Toast.makeText(CreateOneTask.this, "Could not create one-time task",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PetDoc> call_p, Throwable t) {
                Toast.makeText(CreateOneTask.this, "Error. Network connection :(", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
