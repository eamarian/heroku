package com;

import com.Time;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/resources")
public class Resources {

    @GET
    @Path("/time")
    @Produces(MediaType.APPLICATION_JSON)
    public Time get() {

        return new Time();
    }
}
