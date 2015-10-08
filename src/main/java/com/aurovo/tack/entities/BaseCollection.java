package com.aurovo.tack.entities;

import com.google.gson.Gson;
import java.util.Date;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;

/**
 *
 * Base entity for collection
 */
public abstract class BaseCollection {
        
    //Object Id
    @Id
    protected ObjectId id;
    
    //Created date
    protected Date createdDate;
    
    //Modified date
    protected Date modifiedDate;
    
    
    public BaseCollection() {
        super();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    /**
     * Update the date prior to a save/update
     */
    @PrePersist
    public void prePersist() {
        this.createdDate = (createdDate == null) ? new Date() : createdDate;
        this.modifiedDate = (modifiedDate == null) ? createdDate : new Date();
    }
    
    //Implement 
    //public abstract String toString();
    
    //Convert to JSON
    public JSONObject toJSON() throws JSONException {
        
        JSONObject json;
        
        Gson gson = new Gson();
        json = new JSONObject(gson.toJson(this));
        
        return json;
    }
    
    //Convert JSON to object
    public Object fromJSON(JSONObject json) {
        Gson gson = new Gson();
        return gson.fromJson(json.toString(), this.getClass());
    }
    
}
