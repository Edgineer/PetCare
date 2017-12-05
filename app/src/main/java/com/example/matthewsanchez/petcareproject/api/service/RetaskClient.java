package com.example.matthewsanchez.petcareproject.api.service;

import java.util.List;

import com.example.matthewsanchez.petcareproject.api.model.RetaskDoc;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by MatthewSanchez on 12/3/17.
 */

public interface RetaskClient {
    @GET("/retasks/{taskId}")
    Call<RetaskDoc> getRetask(@Path("taskId") String taskId);

    @PUT("/retasks/reset")
    Call<RetaskDoc> resetRetasks();
}
