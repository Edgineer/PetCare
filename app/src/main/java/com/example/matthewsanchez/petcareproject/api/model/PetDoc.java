package com.example.matthewsanchez.petcareproject.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MatthewSanchez on 11/30/17.
 */

public class PetDoc {
    @SerializedName("_id")
    private String ObjectId;

    @SerializedName("name")
    private String name;

    @SerializedName("taskIds")
    private List<String> taskIds;

    public void setObjectId(String ObjectId) { this.ObjectId = ObjectId; }
    public String getObjectId() { return ObjectId; }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setTaskIds(List<String> taskIds) { this.taskIds = taskIds; }
    public List<String> getTaskIds() { return taskIds; }
}
