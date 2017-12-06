package com.example.matthewsanchez.petcare.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MatthewSanchez on 11/30/17.
 */

public class PetDoc implements Serializable {
    @SerializedName("_id")
    private String ObjectId;

    @SerializedName("name")
    private String name;

    @SerializedName("taskIds")
    private List<String> taskIds;

    @SerializedName("taskNames")
    private List<String> taskNames;

    @SerializedName("retaskIds")
    private List<String> retaskIds;

    @SerializedName("retaskNames")
    private List<String> retaskNames;

    public void setObjectId(String ObjectId) { this.ObjectId = ObjectId; }
    public String getObjectId() { return ObjectId; }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setTaskIds(List<String> taskIds) { this.taskIds = taskIds; }
    public List<String> getTaskIds() { return taskIds; }

    public void setTaskNames(List<String> taskNames) { this.taskNames = taskNames; }
    public List<String> getTaskNames() { return taskNames; }

    public void setRetaskIds(List<String> retaskIds) { this.retaskIds = retaskIds; }
    public List<String> getRetaskIds() { return retaskIds; }

    public void setRetaskNames(List<String> retaskNames) { this.retaskNames = retaskNames; }
    public List<String> getRetaskNames() { return retaskNames; }
}
