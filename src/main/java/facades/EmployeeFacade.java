package facades;

import dtos.EmployeeDTO;
import dtos.EmployeeWithSalaryDTO;
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
    
    // Uses DTO constructor to create DTO
    public EmployeeDTO getEmployeeById(int id)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            Employee e = em.find(Employee.class, id);
            return new EmployeeDTO(e);
        }
        finally
        {
            em.close();
        }
    }
    
    // Uses static method to iterate list and convert to DTO
    public List<EmployeeDTO> getEmployeesByName(String name)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            TypedQuery<Employee> q = em.createQuery(
                    "SELECT e FROM Employee e WHERE e.name = :name", Employee.class);
            q.setParameter("name", name);
            List<Employee> res = q.getResultList();
            return EmployeeDTO.getDtos(res);
        }
        finally
        {
            em.close();
        }
    }
    
    // Uses a Constructor Expression to create DTO
    public List<EmployeeDTO> getAllEmployees()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            TypedQuery<EmployeeDTO> q = em.createQuery(
                    "SELECT NEW dtos.EmployeeDTO(e) FROM Employee e", EmployeeDTO.class);
            return q.getResultList();
        }
        finally
        {
            em.close();
        }
    }
    
    // if I had to return another list, I would try Beate's technique of casting to Wildcards. (List<DTO>) (List<?>).
    
    public EmployeeDTO getEmployeeWithHighestSalary()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            TypedQuery<EmployeeDTO> q = em.createQuery(
                    "SELECT NEW dtos.EmployeeDTO(e) FROM Employee e ORDER BY e.salary DESC", EmployeeDTO.class);
            q.setMaxResults(1);
            return q.getSingleResult();
        }
        finally
        {
            em.close();
        }
    }
    
    public EmployeeDTO create(EmployeeDTO dto)
    {
        EntityManager em = emf.createEntityManager();
        Employee entity = new Employee(dto.getName(), dto.getAddress(), null);
        try
        {
            em.getTransaction().begin();
                em.persist(entity);
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
        return new EmployeeDTO(entity);
    }
    
    public EmployeeWithSalaryDTO create(EmployeeWithSalaryDTO dto)
    {
        EntityManager em = emf.createEntityManager();
        Employee entity = new Employee(dto.getName(), dto.getAddress(), dto.getSalary());
        try
        {
            em.getTransaction().begin();
                em.persist(entity);
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
        return new EmployeeWithSalaryDTO(entity);
    }
}
