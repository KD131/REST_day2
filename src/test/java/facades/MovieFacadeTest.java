package facades;

import dtos.MovieDTO;
import entities.Movie;
import entities.Movie;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MovieFacadeTest
{
    
    private static EntityManagerFactory emf;
    private static MovieFacade facade;
    
    @BeforeAll
    static void beforeAll()
    {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = MovieFacade.getMovieFacade(emf);
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
                em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
                em.createNamedQuery("Movie.resetPK").executeUpdate();
    
                em.persist(new Movie(2005, "Star Wars: Episode III - Revenge of the Sith", new String[]{
                        "Hayden Christensen", "Natalie Portman", "Ewan McGregor", "Samuel L. Jackson", "Ian McDiarmid"}));
                em.persist(new Movie(2007, "Stardust", new String[]{
                        "Charlie Cox", "Claire Danes", "Ian McKellen", "Sienna Miller", "Robert De Niro", "Michelle Pfeiffer", "Henry Cavill"}));
                
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
    void getMovieById()
    {
        MovieDTO res = facade.getMovieById(1);
        assertNotNull(res);
    }
    
    @Test
    void getMoviesByTitle()
    {
        List<MovieDTO> res = facade.getMoviesByTitle("Stardust");
        assertEquals(1, res.size());
        assertEquals("Stardust", res.get(0).getTitle());
    }
    
    @Test
    void getAllMovies()
    {
        List<MovieDTO> res = facade.getAllMovies();
        assertNotNull(res);
        assertEquals(2, res.size());
    }
    
    @Test
    void createMovie()
    {
        MovieDTO m1 = new MovieDTO(1999, "Star Wars: Episode I - The Phantom Menace", new String[]{
                "Ewan McGregor", "Liam Neeson", "Natalie Portman", "Jake Lloyd", "Ian McDiarmid", "Ahmed Best", "Ray Park"});
        MovieDTO m2 = facade.create(m1);
        
        assertEquals(3, m2.getId());
        assertEquals("Star Wars: Episode I - The Phantom Menace", facade.getMovieById(3).getTitle());
        assertEquals(3, facade.getAllMovies().size());
    }
    
    @Test
    void numberOfMovies()
    {
        Long count = facade.numberOfMovies();
        assertEquals(2, count);
    }
}