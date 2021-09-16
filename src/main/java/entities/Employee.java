package entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NamedQuery(name = "Employee.deleteAllRows", query = "DELETE from Employee")
@NamedNativeQuery(name = "Employee.resetPK", query = "ALTER table Employee AUTO_INCREMENT = 1")
public class Employee
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    private String address;
    private BigDecimal salary;
    
    public Employee()
    {
    
    }
    
    public Employee(String name, String address, BigDecimal salary)
    {
        this.name = name;
        this.address = address;
        this.salary = salary;
    }
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public BigDecimal getSalary()
    {
        return salary;
    }
    
    public void setSalary(BigDecimal salary)
    {
        this.salary = salary;
    }
}
