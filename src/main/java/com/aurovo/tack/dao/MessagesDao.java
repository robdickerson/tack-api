
package com.aurovo.tack.dao;

import com.aurovo.tack.entities.Message;
import com.aurovo.tack.interfaces.MessageEntityDao;
import org.bson.types.ObjectId;

public class MessagesDao extends BaseDao<Message, ObjectId> 
    implements MessageEntityDao {
        
    public MessagesDao() {
        //Define the class and collection
        super(Message.class, "messages");
    }
}