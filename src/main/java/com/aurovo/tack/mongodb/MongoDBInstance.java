package com.aurovo.tack.mongodb;

import com.aurovo.tack.entities.BaseCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * 
 * Singleton to create mongoDB instance
 */
public class MongoDBInstance {
    
    //TODO: Move to properties file
    public static final String DB_HOST = "127.0.0.1";
    public static final int DB_PORT = 27017;
    public static final String DB_NAME = "tack";
    
    private static MongoDBInstance instance = null;
    
    private static MongoClient mongoClient = null;
    private static Morphia morphia = null;
    private static Datastore datastore = null;
    
    protected MongoDBInstance() {
        
        MongoClientOptions mongoOptions = MongoClientOptions.builder()
            .socketTimeout(60000) // Wait 1m for a query to finish, https://jira.mongodb.org/browse/JAVA-1076
            .connectTimeout(15000) // Try the initial connection for 15s, http://blog.mongolab.com/2013/10/do-you-want-a-timeout/
            .maxConnectionIdleTime(600000) // Keep idle connections for 10m, so we discard failed connections quickly
            .readPreference(ReadPreference.primaryPreferred()) // Read from the primary, if not available use a secondary
            .build();
        
        mongoClient = new MongoClient(new ServerAddress(DB_HOST, DB_PORT), mongoOptions);
        mongoClient.setWriteConcern(WriteConcern.SAFE);

        //Create the morphia instance
        morphia = new Morphia()
            .mapPackage(BaseCollection.class.getPackage().getName());
        
        //Create the datastore
        datastore = morphia.createDatastore(mongoClient, DB_NAME);        
        datastore.ensureIndexes();
        datastore.ensureCaps();
        
    }
        
    public static MongoDBInstance getInstance() {        
        if(instance == null) {
            instance = new MongoDBInstance();            
        }
        return instance;
    }
    
    public MongoClient getMongo() {
        if(mongoClient == null) {
            instance = new MongoDBInstance();            
        }        
        return mongoClient;
    }
    
    public Morphia getMorphia() {
        if(morphia == null) {
            instance = new MongoDBInstance();            
        }        
        return morphia;
    }
    
    //Singleton to return datastore
    public Datastore getDatastore() {
        if(datastore == null) {
            instance = new MongoDBInstance();            
        }       
        return datastore;
    }
}
