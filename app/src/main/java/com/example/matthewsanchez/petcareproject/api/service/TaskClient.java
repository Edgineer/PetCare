package com.example.matthewsanchez.petcareproject.api.service;

import java.util.List;

import com.example.matthewsanchez.petcareproject.api.model.TaskDoc;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by MatthewSanchez on 12/3/17.
 */

public interface TaskClient {
    @GET("/tasks/{taskId}")
    Call<TaskDoc> getTask(@Path("taskId") String taskId);
}
