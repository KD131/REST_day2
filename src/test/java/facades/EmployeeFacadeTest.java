package facades;

import entities.Employee;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeFacadeTest
{
    
    private static EntityManagerFactory emf;
    private static EmployeeFacade facade;
    
    @BeforeAll
    static void beforeAll()
    {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = EmployeeFacade.getEmployeeFacade(emf);
    }
    
    @AfterAll
    static void afterAll()
    {
    
    }
    
    @BeforeEach
    void setUp()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
                em.createNamedQuery("Employee.deleteAllRows").executeUpdate();
                em.createNamedQuery("Employee.resetPK").executeUpdate();
                em.persist(new Employee("Bob", "1st Street 42", BigDecimal.valueOf(100)));
                em.persist(new Employee("Alice", "2nd Street 43", BigDecimal.valueOf(200)));
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }
    
    @AfterEach
    void tearDown()
    {
    }
    
    @Test
    void getEmployeeById()
    {
        Employee res = facade.getEmployeeById(1);
        assertNotNull(res);
    }
    
    @Test
    void getEmployeesByName()
    {
        List<Employee> res = facade.getEmployeesByName("Bob");
        assertEquals(1, res.size());
        assertEquals("Bob", res.get(0).getName());
    }
    
    @Test
    void getAllEmployees()
    {
        List<Employee> res = facade.getAllEmployees();
        assertNotNull(res);
        assertEquals(2, res.size());
    }
    
    @Test
    void getEmployeeWithHighestSalary()
    {
        Employee res = facade.getEmployeeWithHighestSalary();
        assertNotNull(res);
        assertEquals(BigDecimal.valueOf(200), res.getSalary());
        assertEquals("Alice", res.getName());
    }
    
    @Test
    void createEmployee()
    {
        Employee e = new Employee("Test", "UNKNOWN", BigDecimal.valueOf(9999));
        facade.create(e);
        
        assertEquals(3, e.getId());
        assertEquals(3, facade.getAllEmployees().size());
    }
}