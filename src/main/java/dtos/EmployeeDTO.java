package dtos;

import entities.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDTO
{
    private int id;
    private String name;
    private String address;
    
    public EmployeeDTO(Employee e)
    {
        if (e.getId() != null)
        {
            this.id = e.getId();
        }
        this.name = e.getName();
        this.address = e.getAddress();
    }
    
    public EmployeeDTO(String name, String address)
    {
        this.name = name;
        this.address = address;
    }
    
    public static List<EmployeeDTO> getDtos(List<Employee> entities)
    {
        List<EmployeeDTO> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(new EmployeeDTO(e)));
        return dtos;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getAddress()
    {
        return address;
    }
}
