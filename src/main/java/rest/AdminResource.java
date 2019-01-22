package rest;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author vasou
 */
@Path("admin")
public class AdminResource {

    @GET
    @Path("sample")
    public JsonObject sample() {
        return Json.createObjectBuilder().add("message", "only for the admin!").build();
    }

}
