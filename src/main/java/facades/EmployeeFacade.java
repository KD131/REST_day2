package facades;

import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class EmployeeFacade
{
    
    private static EmployeeFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private EmployeeFacade() {}
    
    
    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static EmployeeFacade getEmployeeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }
    
    public Employee getEmployeeById(int id)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            return em.find(Employee.class, id);
        }
        finally
        {
            em.close();
        }
    }
    
    public List<Employee> getEmployeesByName(String name)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            TypedQuery<Employee> q = em.createQuery(
                    "SELECT e FROM Employee e WHERE e.name = :name", Employee.class);
            q.setParameter("name", name);
            return q.getResultList();
        }
        finally
        {
            em.close();
        }
    }
    
    public List<Employee> getAllEmployees()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            TypedQuery<Employee> q = em.createQuery(
                    "SELECT e FROM Employee e", Employee.class);
            return q.getResultList();
        }
        finally
        {
            em.close();
        }
    }
    
    public Employee getEmployeeWithHighestSalary()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            TypedQuery<Employee> q = em.createQuery(
                    "SELECT e FROM Employee e ORDER BY e.salary DESC", Employee.class);
            q.setMaxResults(1);
            return q.getSingleResult();
        }
        finally
        {
            em.close();
        }
    }
    
    public Employee create(Employee employee)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
                em.persist(employee);
            em.getTransaction().commit();
            return employee;
        }
        finally
        {
            em.close();
        }
    }
}
