package com.example.matthewsanchez.petcare.api.service;

import com.example.matthewsanchez.petcare.api.model.UserDoc;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by MatthewSanchez on 11/30/17.
 */

public interface UserClient {
    //@FormUrlEncoded
    @POST("/users/signup/{username}/{password}")
    Call<UserDoc> signup(@Path("username") String username, @Path("password") String password);

    //@FormUrlEncoded
    @GET("/users/login/{username}/{password}")
    Call<UserDoc> login(@Path("username") String username, @Path("password") String password);

    @POST("/users/createpet/{userId}/{petName}")
    Call<UserDoc> createPet(@Path("userId") String userId, @Path("petName") String petName);

    @PUT("/users/addpet/{userId}/{petId}")
    Call<UserDoc> addPet(@Path("userId") String userId, @Path("petId") String petId);
}
