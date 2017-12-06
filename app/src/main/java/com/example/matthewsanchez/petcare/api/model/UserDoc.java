package com.example.matthewsanchez.petcare.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.io.Serializable;

/**
 * Created by MatthewSanchez on 11/30/17.
 */

public class UserDoc implements Serializable {
    @SerializedName("_id")
    private String ObjectId;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("petIds")
    private List<String> petIds;

    @SerializedName("petNames")
    private List<String> petNames;

    public void setObjectId(String ObjectId) { this.ObjectId = ObjectId; }
    public String getObjectId() { return ObjectId; }

    public void setUsername(String username) { this.username = username; }
    public String getUsername() {
        return username;
    }

    public void setPassword(String password) { this.password = password; }
    public String getPassword() {
        return password;
    }

    public void setPetIds(List<String> petIds) { this.petIds = petIds; }
    public List<String> getPetIds() { return petIds; }

    public void setpetNames(List<String> petNames) { this.petNames = petNames; }
    public List<String> getpetNames() { return petNames; }
}
