package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MovieDTO;
import facades.MovieFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/movie")
public class MovieResource
{
    
    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static MovieFacade FACADE = MovieFacade.getMovieFacade(EMF);
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllMovies()
    {
            List<MovieDTO> res = FACADE.getAllMovies();
            return GSON.toJson(res);
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMovie(@PathParam("id") int id)
    {
        MovieDTO m = FACADE.getMovieById(id);
        return GSON.toJson(m);
    }
    
    @GET
    @Path("/title/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMovie(@PathParam("title") String title)
    {
        List<MovieDTO> res = FACADE.getMoviesByTitle(title);
        return GSON.toJson(res);
    }
    
    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCount()
    {
        long count = FACADE.numberOfMovies();
        return "{\"count\":" + count + "}";
    }
}
