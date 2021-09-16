package facades;

import dtos.MovieDTO;
import entities.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class MovieFacade
{
    
    private static MovieFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private MovieFacade() {}
    
    
    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }
    
    // Uses DTO constructor to create DTO
    public MovieDTO getMovieById(int id)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            Movie m = em.find(Movie.class, id);
            return new MovieDTO(m);
        }
        finally
        {
            em.close();
        }
    }
    
    // Uses static method to iterate list and convert to DTO
    public List<MovieDTO> getMoviesByTitle(String title)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            TypedQuery<Movie> q = em.createQuery(
                    "SELECT m FROM Movie m WHERE m.title = :title", Movie.class);
            q.setParameter("title", title);
            List<Movie> res = q.getResultList();
            return MovieDTO.getDtos(res);
        }
        finally
        {
            em.close();
        }
    }
    
    // Uses a Constructor Expression to create DTO
    public List<MovieDTO> getAllMovies()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            TypedQuery<MovieDTO> q = em.createQuery(
                    "SELECT NEW dtos.MovieDTO(m) FROM Movie m", MovieDTO.class);
            return q.getResultList();
        }
        finally
        {
            em.close();
        }
    }
    
    // if I had to return another list, I would try Beate's technique of casting to Wildcards. (List<DTO>) (List<?>).
    
    public MovieDTO create(MovieDTO dto)
    {
        EntityManager em = emf.createEntityManager();
        Movie entity = new Movie(dto.getYear(), dto.getTitle(), dto.getActors());
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
        return new MovieDTO(entity);
    }
    
    public Long numberOfMovies()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            TypedQuery<Long> q = em.createQuery(
                    "SELECT COUNT(m) FROM Movie m", Long.class);
            return q.getSingleResult();
        }
        finally
        {
            em.close();
        }
    }
}
