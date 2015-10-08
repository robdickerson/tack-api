package com.aurovo.tack.dao;

import com.aurovo.tack.entities.BaseCollection;
import com.aurovo.tack.interfaces.BaseEntityDao;
import com.aurovo.tack.mongodb.MongoDBInstance;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;

/**
 * Defines the Base Data Access Object and functions
 * for querying the database
 * @param <T>
 * @param <K> 
 */
public class BaseDao<T extends BaseCollection, K>
    implements BaseEntityDao<T,K>, Serializable {
        
    private String dbName;    
    private MongoDBInstance mongodb;
        
    private BasicDAO<T,K> basicDao = null;
    private Class clazz;
    
    public BaseDao(Class clazz, String dbName) {
        this.clazz = clazz;
        this.dbName = dbName;
        setupBasicDao();
    }
    
    @PostConstruct
    public void init() {
        setupBasicDao();
    }

    protected BasicDAO<T, K> getBasicDao() {
        if (basicDao == null)
            setupBasicDao();
        return basicDao;
    }

    private void setupBasicDao() {       
        MongoDBInstance mongodb = MongoDBInstance.getInstance();
        
        basicDao = new BasicDAO<T, K>(clazz, 
                mongodb.getMongo(), 
                mongodb.getMorphia(),
                dbName);
        basicDao.ensureIndexes();
    }

    @Override
    public T findById(K id) {
        return getBasicDao().get(id); 
    }

    @Override
    public K save(T o) {
        Key<T> key = getBasicDao().save(o);
        return (K) key.getId();
    }

    @Override
    public T update(T o) {
         return findById((K) o.getId());
    }

    protected QueryResults<T> find( Query<T> query ) {
         return getBasicDao().find(query);
    }

    protected T findOne( Query<T> query ) {
        return getBasicDao().findOne(query);
    }    

    protected Query<T> createQuery() {
        return getBasicDao().getDatastore().createQuery(clazz);
    }
    
    
}
