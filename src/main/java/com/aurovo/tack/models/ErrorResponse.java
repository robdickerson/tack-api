package com.aurovo.tack.models;

import javax.ws.rs.core.Response;

/**
 * Error Response
 */
public class ErrorResponse {
            
    private Response.Status code;
    
    private String status;
    
    private String message;
    
    private String data;
    
    //public ErrorResponse() {}
    
    public ErrorResponse(Response.Status code,             
            String message, 
            String data){
        
        this.code = code;
        this.message = message;        
        this.data = data;
        
        //Generate the status
        this.status = generateStatus(this.code);
    }

    public Response.Status getCode() {
        return code;
    }

    public void setCode(Response.Status code) {
        this.code = code;
    }

    public String getStatus() {
        return generateStatus(this.code);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
        
    /**
     * Generate the status based on the response family
     * @param code
     * @return 
     */
    public String generateStatus(Response.Status code) {
    
        Response.Status.Family family = code.getFamily();
        
        //Return a reponse
        if((family == Response.Status.Family.INFORMATIONAL) ||
            (family == Response.Status.Family.SUCCESSFUL) || 
            (family == Response.Status.Family.REDIRECTION)) {
            
            return "success";
        }
        else if(family == Response.Status.Family.CLIENT_ERROR) {
            return "error";
        }
        
        return "fail";
    }
    
    /**
     * Convert to JSON
     * @return 
     */
    public String toJSON() {       
        return "{ \"code\":"    + String.valueOf(this.code.getStatusCode()) +
                ",\"status\":\""+ this.status + "\""+
                ",\"message\":\""   + this.message + "\""+
                ",\"data\":\""  + this.data + "\""+
                "}";        
    }
    
}
