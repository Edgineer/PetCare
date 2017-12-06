package com.example.matthewsanchez.petcare.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matthewsanchez.petcare.R;
import com.example.matthewsanchez.petcare.api.model.PetDoc;
import com.example.matthewsanchez.petcare.api.model.UserDoc;
import com.example.matthewsanchez.petcare.api.service.PetClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class PetProfileList extends AppCompatActivity {

    public PetClient client_p;
    public Call<PetDoc> call_p;
    PetDoc somePet;
    UserDoc someUser;
    String[] petPro;

    NotificationCompat.Builder notificationBuilder;
    private static final int uniqueID = 4567;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile_list);

        Intent intent = getIntent();
        someUser = (UserDoc)intent.getSerializableExtra("userObject");

        notificationBuilder = new NotificationCompat.Builder(PetProfileList.this, "channel");
        notificationBuilder.setAutoCancel(true);

        notificationBuilder.setSmallIcon(android.R.drawable.stat_notify_error)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("PetCare")
                .setContentText("Check up on your Pets' daily tasks!")
                .setTicker("Check up on your Pets' daily tasks!");

        Intent notintent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notintent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);

        notificationBuilder.setDefaults(
                Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(PetProfileList.this);
        notificationManager.notify(1, notificationBuilder.build());


        EditText editText3 = (EditText) findViewById(R.id.editText3);
        editText3.setInputType(InputType.TYPE_NULL);

        //EditText editText2 = (EditText) findViewById(R.id.editText2);
        //editText2.setInputType(InputType.TYPE_NULL);

        List<String> listOfPetIDs = someUser.getPetIds();
        String[] petIds = new String[listOfPetIDs.size()];
        for(int i = 0; i < listOfPetIDs.size(); i++) {
            petIds[i] = listOfPetIDs.get(i);
        }

        List<String> listOfPetNames = someUser.getpetNames();
        String[] petNames = new String[listOfPetNames.size()];
        for(int k = 0; k < listOfPetNames.size(); k++) {
            petNames[k] = listOfPetNames.get(k);
        }

        int sizeOfPetList = listOfPetNames.size();
        petPro = new String[sizeOfPetList];
        for (int a = 0; a < sizeOfPetList; a++) {
            petPro[a] = petNames[a] + "\n" + petIds[a];
        }

        for(int j = 0; j < someUser.getpetNames().size(); j++){
            Log.d("TAG", "PET ID IS: " + someUser.getPetIds().get(j));
            Log.d("TAG", "PET NAME IS: " + listOfPetNames.get(j));
        }

        //String[] foods = {"Bacon", "Ham", "Tuna", "Candy", "Meatball", "Potato"};
        ListAdapter mattsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, petPro);
        ListView mattsListView = (ListView) findViewById(R.id.mattsListView);
        mattsListView.setAdapter(mattsAdapter);

        final Intent taskIntent = new Intent(this, TaskList.class);

        mattsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                        String onePet = String.valueOf(parent.getItemAtPosition(position));
                        String lines[] = onePet.split("\\r?\\n");
                        String petId = lines[1];
                        //Toast.makeText(PetProfileList.this, lines[1], Toast.LENGTH_LONG).show();

                        // make retrofit object
                        Retrofit.Builder builder = new Retrofit.Builder()
                                .baseUrl("https://serene-retreat-67526.herokuapp.com/")
                                .addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit = builder.build();

                        // Get pet doc to fill up task list activity
                        PetClient client_p = retrofit.create(PetClient.class);
                        Call<PetDoc> call_p = client_p.petById(petId);
                        call_p.enqueue(new Callback<PetDoc>() {
                            @Override
                            public void onResponse(Call<PetDoc> call_p, Response<PetDoc> response) {
                                if (response.isSuccessful()) {
                                    somePet = response.body();
                                    Log.d("TAG", "PETNAME IS: " + somePet.getName());
                                    Log.d("TAG", "PETID IS: " + somePet.getObjectId());
                                    Log.d("TAG", "TASKS ARE IS" + somePet.getTaskNames());


                                    taskIntent.putExtra("petObject", somePet);
                                    startActivity(taskIntent);

                                } else {
                                    Log.d("TAG", "UNSUCCESSFUL, RETURN CODE: " + response.code() + " (invalid PetID)");
                                    Toast.makeText(PetProfileList.this, "Pet does not exist",
                                            Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<PetDoc> call_p, Throwable t) {
                                Toast.makeText(PetProfileList.this, "Error. Network connection :(", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
        );

    }





    static final int Pet_REQUEST = 1;

    public void goToCreate(View view) {
        Intent intent = new Intent(this, createGroup.class);
        intent.putExtra("userCreateObject", someUser);
        //startActivity(intent);
        startActivityForResult(intent, Pet_REQUEST);
    }

    static final int JOIN_REQUEST = 2;

    public void goToJoin(View view) {
        Intent intent = new Intent(this, createJoin.class);
        intent.putExtra("userJoinObject", someUser);
        startActivityForResult(intent, JOIN_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        if (requestCode == Pet_REQUEST && resultCode == RESULT_OK) {

            Log.d("TAG", "ACTIVITY HAS RETURNED");

            Intent intent = getIntent();
            someUser = (UserDoc) data.getExtras().getSerializable("key");

                List<String> listOfPetIDs = someUser.getPetIds();
                String[] petIds = new String[listOfPetIDs.size()];
                for(int i = 0; i < listOfPetIDs.size(); i++) {
                    petIds[i] = listOfPetIDs.get(i);
                }

                List<String> listOfPetNames = someUser.getpetNames();
                String[] petNames = new String[listOfPetNames.size()];
                for(int k = 0; k < listOfPetNames.size(); k++) {
                    petNames[k] = listOfPetNames.get(k);
                }

                int sizeOfPetList = listOfPetNames.size();
                String[] petPro = new String[sizeOfPetList];
                for (int a = 0; a < sizeOfPetList; a++) {
                    petPro[a] = petNames[a] + "\n" + petIds[a];
                }

                ListAdapter mattsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, petPro);
                ListView mattsListView = (ListView) findViewById(R.id.mattsListView);
                mattsListView.setAdapter(mattsAdapter);

        }

        else if (requestCode == JOIN_REQUEST && resultCode == RESULT_OK) {
            Log.d("TAG", "JOIN HAS RETURNED");

            Intent intent = getIntent();
            someUser = (UserDoc) data.getExtras().getSerializable("key2");


            List<String> listOfPetIDs = someUser.getPetIds();
            String[] petIds = new String[listOfPetIDs.size()];
            for(int i = 0; i < listOfPetIDs.size(); i++) {
                petIds[i] = listOfPetIDs.get(i);
            }

            List<String> listOfPetNames = someUser.getpetNames();
            String[] petNames = new String[listOfPetNames.size()];
            for(int k = 0; k < listOfPetNames.size(); k++) {
                petNames[k] = listOfPetNames.get(k);
            }

            int sizeOfPetList = listOfPetNames.size();
            String[] petPro = new String[sizeOfPetList];
            for (int a = 0; a < sizeOfPetList; a++) {
                petPro[a] = petNames[a] + "\n" + petIds[a];
            }

            ListAdapter mattsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, petPro);
            ListView mattsListView = (ListView) findViewById(R.id.mattsListView);
            mattsListView.setAdapter(mattsAdapter);

        }
    }


}
