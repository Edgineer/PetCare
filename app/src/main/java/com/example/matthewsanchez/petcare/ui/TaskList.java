package com.example.matthewsanchez.petcare.ui;

import android.content.Intent;
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
import com.example.matthewsanchez.petcare.api.service.PetClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskList extends AppCompatActivity {

    PetDoc somePet;

    /*
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 10);
    calendar.set(Calendar.MINUTE, 30);
    calendar.set(Calendar.SECOND, 0);

    Date alarmTime = calendar.getTime();

    Timer _timer = new Timer();
    _timer.schedule(foo, alarmTime);
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        Intent intent = getIntent();
        somePet = (PetDoc) intent.getSerializableExtra("petObject");

        List<String> listOfTaskNames = somePet.getRetaskNames();
        String[] reTaskNames = new String[listOfTaskNames.size()];
        for (int i = 0; i < listOfTaskNames.size(); i++) {
            reTaskNames[i] = listOfTaskNames.get(i);
        }

        ListAdapter mattsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, reTaskNames);
        ListView mattsTaskListView = (ListView) findViewById(R.id.mattsTaskListView);
        mattsTaskListView.setAdapter(mattsAdapter);


        List<String> listOfSingleTaskNames = somePet.getTaskNames();
        String[] singleTaskNames = new String[listOfSingleTaskNames.size()];
        for (int i = 0; i < listOfSingleTaskNames.size(); i++) {
            singleTaskNames[i] = listOfSingleTaskNames.get(i);
        }

        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setInputType(InputType.TYPE_NULL);

        EditText editText2 = (EditText) findViewById(R.id.editText2);
        editText2.setInputType(InputType.TYPE_NULL);

        ListAdapter mattsAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, singleTaskNames);
        ListView mattsTaskListView2 = (ListView) findViewById(R.id.mattsTaskListView2);
        mattsTaskListView2.setAdapter(mattsAdapter2);


        final Intent taskIntent = new Intent(this, TaskList.class);

        // RECURRING TASKS ON CLICK
        mattsTaskListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        //List<String> listOfSingleTaskIDs = somePet.geTaskIds();
                        //List<String> listOfReTaskIDs = somePet.getRetaskIds();
                        String retaskId = somePet.getRetaskIds().get(position);
                        String petId = somePet.getObjectId();
                        Log.d("TAG", "Task name " + String.valueOf(parent.getItemAtPosition(position)) + " has Id of " + retaskId);

                        // make retrofit object
                        Retrofit.Builder builder = new Retrofit.Builder()
                                .baseUrl("https://serene-retreat-67526.herokuapp.com/")
                                .addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit = builder.build();

                        // Get pet doc to fill up task list activity
                        PetClient client_p = retrofit.create(PetClient.class);
                        // TODO: Decide whether to use .deleteRetask or .completeRetask
                        Call<PetDoc> call_p = client_p.completeRetask(petId, retaskId);
                        call_p.enqueue(new Callback<PetDoc>() {
                            @Override
                            public void onResponse(Call<PetDoc> call_p, Response<PetDoc> response) {
                                if (response.isSuccessful()) {
                                    somePet = response.body();
                                    Log.d("TAG", "PETNAME IS: " + somePet.getName());
                                    Log.d("TAG", "PETID IS: " + somePet.getObjectId());
                                    Log.d("TAG", "RETASKS ARE" + somePet.getRetaskNames());

                                    // Refresh
                                    taskIntent.putExtra("petObject", somePet);
                                    finish();
                                    startActivity(taskIntent);

                                } else {
                                    Log.d("TAG", "UNSUCCESSFUL, RETURN CODE: " + response.code() + " (Retask id not in pet's list of retasks)");
                                    Toast.makeText(TaskList.this, "Could not delete reoccurring task. Other user may have already completed this task. Please refresh page.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<PetDoc> call_p, Throwable t) {
                                Toast.makeText(TaskList.this, "Error. Network connection :(", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
        );

        // SINGLE TASKS ON CLICK
        mattsTaskListView2.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        //List<String> listOfSingleTaskIDs = somePet.getTaskIds();
                        //List<String> listOfReTaskIDs = somePet.getRetaskIds();
                        String taskId = somePet.getTaskIds().get(position);
                        String petId = somePet.getObjectId();
                        Log.d("TAG", "Task name " + String.valueOf(parent.getItemAtPosition(position)) + " has Id of " + taskId);

                        // make retrofit object
                        Retrofit.Builder builder = new Retrofit.Builder()
                                .baseUrl("https://serene-retreat-67526.herokuapp.com/")
                                .addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit = builder.build();

                        // Get pet doc to fill up task list activity
                        PetClient client_p = retrofit.create(PetClient.class);
                        Call<PetDoc> call_p = client_p.deleteTask(petId, taskId);
                        call_p.enqueue(new Callback<PetDoc>() {
                            @Override
                            public void onResponse(Call<PetDoc> call_p, Response<PetDoc> response) {
                                if (response.isSuccessful()) {
                                    somePet = response.body();
                                    Log.d("TAG", "PETNAME IS: " + somePet.getName());
                                    Log.d("TAG", "PETID IS: " + somePet.getObjectId());
                                    Log.d("TAG", "TASKS ARE" + somePet.getTaskNames());

                                    // Refresh
                                    taskIntent.putExtra("petObject", somePet);
                                    finish();
                                    startActivity(taskIntent);

                                } else {
                                    Log.d("TAG", "UNSUCCESSFUL, RETURN CODE: " + response.code() + " (Task id not in pet's list of tasks)");
                                    Toast.makeText(TaskList.this, "Could not delete one-time task. Other user may have already completed this task. Please refresh page.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<PetDoc> call_p, Throwable t) {
                                Toast.makeText(TaskList.this, "Error. Network connection :(", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
        );


    }


    static final int RETASK_REQUEST = 3;

    public void goToReTask(View view) {
        Intent intent = new Intent(this, CreateReTask.class);
        intent.putExtra("petReTaskObject", somePet);
        //startActivity(intent);
        startActivityForResult(intent, RETASK_REQUEST);
    }

    static final int ONETASK_REQUEST = 4;

    public void goToOneTask(View view) {
        Intent intent = new Intent(this, CreateOneTask.class);
        intent.putExtra("petOneTaskObject", somePet);
        startActivityForResult(intent, ONETASK_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        if (requestCode == RETASK_REQUEST && resultCode == RESULT_OK) {

            Log.d("TAG", "Retask HAS RETURNED");

            Intent intent = getIntent();
            somePet = (PetDoc) data.getExtras().getSerializable("key3");

            List<String> listOfTaskNames = somePet.getRetaskNames();
            String[] reTaskNames = new String[listOfTaskNames.size()];
            for (int i = 0; i < listOfTaskNames.size(); i++) {
                reTaskNames[i] = listOfTaskNames.get(i);
            }

            ListAdapter mattsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, reTaskNames);
            ListView mattsTaskListView = (ListView) findViewById(R.id.mattsTaskListView);
            mattsTaskListView.setAdapter(mattsAdapter);


            List<String> listOfSingleTaskNames = somePet.getTaskNames();
            String[] singleTaskNames = new String[listOfSingleTaskNames.size()];
            for (int i = 0; i < listOfSingleTaskNames.size(); i++) {
                singleTaskNames[i] = listOfSingleTaskNames.get(i);
            }

            ListAdapter mattsAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, singleTaskNames);
            ListView mattsTaskListView2 = (ListView) findViewById(R.id.mattsTaskListView2);
            mattsTaskListView2.setAdapter(mattsAdapter2);


        } else if (requestCode == ONETASK_REQUEST && resultCode == RESULT_OK) {
            Log.d("TAG", "OneTask HAS RETURNED");

            Intent intent = getIntent();
            somePet = (PetDoc) data.getExtras().getSerializable("key4");


            List<String> listOfTaskNames = somePet.getRetaskNames();
            String[] reTaskNames = new String[listOfTaskNames.size()];
            for (int i = 0; i < listOfTaskNames.size(); i++) {
                reTaskNames[i] = listOfTaskNames.get(i);
            }

            ListAdapter mattsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, reTaskNames);
            ListView mattsTaskListView = (ListView) findViewById(R.id.mattsTaskListView);
            mattsTaskListView.setAdapter(mattsAdapter);


            List<String> listOfSingleTaskNames = somePet.getTaskNames();
            String[] singleTaskNames = new String[listOfSingleTaskNames.size()];
            for (int i = 0; i < listOfSingleTaskNames.size(); i++) {
                singleTaskNames[i] = listOfSingleTaskNames.get(i);
            }

            ListAdapter mattsAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, singleTaskNames);
            ListView mattsTaskListView2 = (ListView) findViewById(R.id.mattsTaskListView2);
            mattsTaskListView2.setAdapter(mattsAdapter2);


        }
    }


    /*
    final Intent reAddIntent = new Intent(this, TaskList.class);

    public void doThis() {
        ////////// PUT IN ON_TIME_RESET FUNCTION
        // make retrofit object
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://serene-retreat-67526.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        // Get pet doc to fill up task list activity
        RetaskClient client_r = retrofit.create(RetaskClient.class);
        Call<RetaskDoc> call_r = client_r.resetRetasks();
        call_r.enqueue(new Callback<RetaskDoc>() {
            @Override
            public void onResponse(Call<RetaskDoc> call_r, Response<RetaskDoc> response) {
                if (response.isSuccessful()) {
                    // Refresh


                    reAddIntent.putExtra("petObject", somePet);
                    finish();
                    startActivity(reAddIntent);

                } else {
                    Log.d("TAG", "UNSUCCESSFUL, RETURN CODE: " + response.code() + " (Retask could not be readded to pet list)");
                    Toast.makeText(TaskList.this, "Could not readd reoccurring task.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RetaskDoc> call_p, Throwable t) {
                Toast.makeText(TaskList.this, "Error. Network connection :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
    */



}
