package org.aicha.servlet;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import jakarta.inject.Inject;
import org.aicha.controller.TaskAPIController;

@Path("/")
public class TaskAPIResource {

    @Inject
    private TaskAPIController taskAPIController;

    // Handle POST requests with a JSON body
    @POST
    @Path("/tasks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks(TaskFilterRequest requestBody) {
        return taskAPIController.index(requestBody.getTagsIds(), requestBody.getYear(), requestBody.getMonth());
    }
}
