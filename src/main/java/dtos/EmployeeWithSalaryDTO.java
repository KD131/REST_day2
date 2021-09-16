package dtos;

import entities.Employee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EmployeeWithSalaryDTO
{
    private int id;
    private String name;
    private String address;
    private BigDecimal salary;
    
    public EmployeeWithSalaryDTO(Employee e)
    {
        this.id = e.getId();
        this.name = e.getName();
        this.address = e.getAddress();
        this.salary = e.getSalary();
    }
    
    public static List<EmployeeWithSalaryDTO> getDtos(List<Employee> entities)
    {
        List<EmployeeWithSalaryDTO> dtos = new ArrayList<>();
        entities.forEach(e -> dtos.add(new EmployeeWithSalaryDTO(e)));
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
    
    public BigDecimal getSalary()
    {
        return salary;
    }
}
