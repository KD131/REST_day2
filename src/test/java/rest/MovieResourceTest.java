package rest;

import entities.Movie;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.print.attribute.standard.Media;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class MovieResourceTest
{
    
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    
    private static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    
    static HttpServer startServer()
    {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }
    
    @BeforeAll
    static void beforeAll()
    {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }
    
    @AfterAll
    static void afterAll()
    {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }
    
    @BeforeEach
    void setUp()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            
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
    
    // I thought the deployment bug was because the deletes were before the persists, but that's not the case, so I don't need this AfterEach.
    // But I left it because why not.
    @AfterEach
    void tearDown()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
                em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
                em.createNamedQuery("Movie.resetPK").executeUpdate();
            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }
    
    @Test
    void serverIsUp()
    {
        given().when().get("/movie").then().statusCode(200);
    }
    
    @Test
    void getAllMovies()
    {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .get("/movie/all")
                .then()
                .assertThat().statusCode(200)
                .body("size", equalTo(2));
    }
    
    @Test
    void getMovieById()
    {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .get("/movie/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(1));
    }
    
    @Test
    void getMovieByTitle()
    {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .get("movie/title/Stardust")
                .then()
                .assertThat()
                .statusCode(200)
                .body("title", hasItems("Stardust"));
    }
    
    @Test
    void getMovieLike()
    {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .get("movie/title-like/star")
                .then()
                .assertThat()
                .statusCode(200)
                .body("size", equalTo(2));
        
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .get("movie/title-like/dust")
                .then()
                .assertThat()
                .statusCode(200)
                .body("title", hasItems("Stardust"));
    }
    
    @Test
    void getCount()
    {
        given()
                .when()
                .get("/movie/count")
                .then()
                .assertThat()
                .body("count", equalTo(2));
    }
}