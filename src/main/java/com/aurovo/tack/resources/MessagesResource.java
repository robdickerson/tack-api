package com.aurovo.tack.resources;

import com.aurovo.tack.dao.MessagesDao;
import com.aurovo.tack.entities.Message;
import com.aurovo.tack.models.ErrorResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.json.JSONException;

/**
 * REST Web Service
 * /messages
 */
@Path("messages")
public class MessagesResource {

    //Logger
    private static Log log = LogFactory.getLog(MessagesResource.class);
        
    private MessagesDao dao;
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TackResource
     */
    public MessagesResource() {
                         
        //Create the dao
        dao = new MessagesDao();
    }

    
    //TODO: COMPLETE GET, POST, PUT and DELETE
    
    /**
     * GET /messages/:id
     * @param id
     * @return 
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("id") String id) {
       
        //Validate Id - 400 response
        if(!ObjectId.isValid(id)) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(Response.Status.BAD_REQUEST, "invalid_request_error", "Id Invalid").toJSON().toString())
                .build();
        }
        
        // Execute the database call
        Message msg = dao.findById(new ObjectId(id));
        
        //Validate response
        if(msg == null) {            
                    
            //404 NOT FOUND
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(Response.Status.NOT_FOUND, "not_found_error", "Not found").toJSON().toString())
                    .build();
        }
                
        try {
            //Return successful response
            return Response
                    .ok(msg.toJSON().toString(), MediaType.APPLICATION_JSON)
                    .build();                                   
            
                    /*.entity(new ErrorResponse(Response.Status.OK, "not_found_error", "Not found").toJSON().toString())
                    .build();*/                    
                    
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        //400 BAD REQUEST
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(Response.Status.BAD_REQUEST, "invalid_request_error", "Invalid request").toJSON().toString())
                .build();
        
    }

    /**
     * POST /messages
     * @param msg
     * @return 
     */
    @POST    
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(Message msg) {
     
        //TODO: Validate the message
                
        //Save the response
        ObjectId id = dao.save(msg);
        
        //TODO: Check response 
        
        //Return successful response
        return Response.status(Response.Status.CREATED)
                .entity(new ErrorResponse(Response.Status.CREATED, "", "").toJSON().toString())
                .build();
        
    }
    
    
}
