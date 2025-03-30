package cst8218.n041092398.cst8218.jn.resources;

import cst8218.n041092398.bouncer.entity.Bouncer;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Julian
 */
@Path("jakartaee10")
public class JakartaEE10Resource {
    private static Map<Integer, Bouncer> bouncerDB = new HashMap<>();
    private static int currentId = 1;  // Simulating auto-increment

    /**
     * Check API availability
     */
    @GET
    @Path("ping")
    public Response ping(){
        return Response.ok("API is up").build();
    }

}
