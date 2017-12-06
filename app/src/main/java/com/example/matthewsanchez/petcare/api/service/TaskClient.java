package com.example.matthewsanchez.petcare.api.service;

import com.example.matthewsanchez.petcare.api.model.TaskDoc;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by MatthewSanchez on 12/3/17.
 */

public interface TaskClient {
    @GET("/tasks/{taskId}")
    Call<TaskDoc> getTask(@Path("taskId") String taskId);
}
