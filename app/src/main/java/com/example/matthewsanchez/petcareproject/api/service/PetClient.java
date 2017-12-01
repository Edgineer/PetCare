package com.example.matthewsanchez.petcareproject.api.service;

import java.util.List;

import com.example.matthewsanchez.petcareproject.api.model.PetDoc;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by MatthewSanchez on 11/30/17.
 */

public interface PetClient {

    @GET("/pets/me/{petId}")
    Call<PetDoc> petById(@Path("petId") String petId);

    @POST("/pets/createtask/{petId}/{text}")
    Call<PetDoc> createTask(@Path("petId") String petId, @Path("text") String task);

    @DELETE("/pets/deletetask/{petId}/{taskId}")
    Call<PetDoc> completeTask(@Path("petId") String petId, @Path("taskId") String taskId);

    @POST("/pets/createretask/{petId}/{text}")
    Call<PetDoc> createRetask(@Path("petId") String petId, @Path("text") String task);

    @PUT("/pets/deleteretask/{petId}/{retaskId}")
    Call<PetDoc> completeRetask(@Path("petId") String petId, @Path("retaskId") String retaskId);
}
