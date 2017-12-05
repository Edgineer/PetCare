package com.example.matthewsanchez.petcareproject.api.model;

import java.io.Serializable;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MatthewSanchez on 12/3/17.
 */

public class TaskDoc implements Serializable {
    @SerializedName("_id")
    private String ObjectId;

    @SerializedName("text")
    private String text;

    @SerializedName("completed")
    private boolean completed;

    public void setObjectId(String ObjectId) { this.ObjectId = ObjectId; }
    public String getObjectId() { return ObjectId; }

    public void setText(String text) { this.text = text; }
    public String getText() { return text; }

    public void setCompleted(boolean completed) { this.completed = completed; }
    public boolean getCompleted() { return completed; }
}
