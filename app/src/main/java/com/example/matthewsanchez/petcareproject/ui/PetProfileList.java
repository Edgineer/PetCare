package com.example.matthewsanchez.petcareproject.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matthewsanchez.petcareproject.R;
import com.example.matthewsanchez.petcareproject.api.model.PetDoc;
import com.example.matthewsanchez.petcareproject.api.model.UserDoc;
import com.example.matthewsanchez.petcareproject.api.service.PetClient;
import com.example.matthewsanchez.petcareproject.api.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class PetProfileList extends AppCompatActivity {

    public PetClient client_p;
    public Call<PetDoc> call_p;
    public PetDoc somePet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile_list);

        Intent intent = getIntent();
        UserDoc someUser = (UserDoc)intent.getSerializableExtra("userObject");

        List<String> listOfPetIDs = someUser.getPetIds();
        String[] petIds = new String[listOfPetIDs.size()];
        int i = 0;
        while( ! listOfPetIDs.isEmpty() ) {
            String element = listOfPetIDs.get(0);
            petIds[i] = element;
            i++;
            listOfPetIDs = listOfPetIDs.subList(1, listOfPetIDs.size());
        }

        // make retrofit object
        /*Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://serene-retreat-67526.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        String[] petNames = new String[listOfPetIDs.size()];
        // Make request to get pet name *associated* with pet id
        client_p = ServiceGenerator.createService(PetClient.class);
        for(int j = 0; j < someUser.getPetIds().size(); j++){
            call_p = client_p.petById(someUser.getPetIds().get(j));
            final int k = j;
            try {
                somePet = call_p.execute().body();
                petNames[k] = somePet.getName();
            } catch (IOException exception) {
                Log.d("TAG", "Problem executing");
            }
            /*call_p.enqueue(new Callback<PetDoc>() {
                @Override
                public void onResponse(Call<PetDoc> call_p, Response<PetDoc> response) {
                    if (response.isSuccessful()) {
                        somePet = response.body();
                        //petNames[k] = somePet.getName();
                        Log.d("TAG", "PET NAME IS: " + somePet.getName());
                        Log.d("TAG", "PET ID IS: " + somePet.getObjectId());
                    } else {
                        Log.d("TAG", "UNSUCCESSFUL, RETURN CODE: " + response.code() + " (Bad petId)");
                    }
                }
                @Override
                public void onFailure(Call<PetDoc> call_p, Throwable t) {
                    Toast.makeText(PetProfileList.this, "error :(", Toast.LENGTH_SHORT).show();
                }
            });*/
            /*if (somePet == null)
                Log.d("TAG", "null pet");
            else
                Log.d("TAG", "Your pet has data");
        }

        /*for (int k = 0; k < someUser.getPetIds().size(); k++) {
           Log.d("TAG", "Pet Id " + petIds[k] + " and Pet Name " + petNames[k]);
        }*/

        //String[] foods = {"Bacon", "Ham", "Tuna", "Candy", "Meatball", "Potato"};
        ListAdapter mattsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, petIds);
        ListView mattsListView = (ListView) findViewById(R.id.mattsListView);
        mattsListView.setAdapter(mattsAdapter);

        /*
        mattsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String food = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(PetProfileList.this, food, Toast.LENGTH_LONG).show();
                    }
                }
        );
        */
    }
}
