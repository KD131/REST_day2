package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.EmployeeDTO;
import facades.EmployeeFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/employee")
public class EmployeeResource
{
    
    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static EmployeeFacade FACADE = EmployeeFacade.getEmployeeFacade(EMF);
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllEmployees()
    {
            List<EmployeeDTO> res = FACADE.getAllEmployees();
            return GSON.toJson(res);
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmployee(@PathParam("id") int id)
    {
        EmployeeDTO e = FACADE.getEmployeeById(id);
        return GSON.toJson(e);
    }
    
    @GET
    @Path("/highestpaid")
    @Produces(MediaType.APPLICATION_JSON)
    public String getHighestPaid()
    {
        EmployeeDTO e = FACADE.getEmployeeWithHighestSalary();
        return GSON.toJson(e);
    }
    
    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmployee(@PathParam("name") String name)
    {
        List<EmployeeDTO> res = FACADE.getEmployeesByName(name);
        return GSON.toJson(res);
    }
}