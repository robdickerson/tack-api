
package com.aurovo.tack.interfaces;

import com.aurovo.tack.entities.Message;
//import java.util.List;
import org.bson.types.ObjectId;

public interface MessageEntityDao extends BaseEntityDao<Message, ObjectId> {
    
    //TODO: Add other custom methods as necessary
    //List<MessageEntity> findAll();
}
