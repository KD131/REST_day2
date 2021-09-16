/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.EmployeeWithSalaryDTO;
import dtos.RenameMeDTO;
import entities.Employee;
import entities.RenameMe;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

import java.math.BigDecimal;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        FacadeExample fe = FacadeExample.getFacadeExample(emf);
        fe.create(new RenameMeDTO(new RenameMe("First 1", "Last 1")));
        fe.create(new RenameMeDTO(new RenameMe("First 2", "Last 2")));
        fe.create(new RenameMeDTO(new RenameMe("First 3", "Last 3")));
    }
    public static void populateEmployee(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EmployeeFacade facade = EmployeeFacade.getEmployeeFacade(emf);
    
        // resets the table
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        em.createNamedQuery("Employee.deleteAllRows").executeUpdate();
//        em.createNamedQuery("Employee.resetPK").executeUpdate();
//        em.getTransaction().commit();
        
        // this whole WithSalary DTO and the overloaded .create() method is so shoddy.
        // TODO ask teacher how to create DTOs with salary in the facade.
        facade.create(new EmployeeWithSalaryDTO(new Employee("Bob", "1st Street 42", BigDecimal.valueOf(100))));
        facade.create(new EmployeeWithSalaryDTO(new Employee("Alice", "2nd Street 43", BigDecimal.valueOf(200))));
    }
    
    public static void main(String[] args) {
        populateEmployee();
    }
}
