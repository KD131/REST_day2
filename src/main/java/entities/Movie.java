package entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NamedQuery(name = "Movie.deleteAllRows", query = "DELETE from Movie ")
@NamedNativeQuery(name = "Movie.resetPK", query = "ALTER table Movie AUTO_INCREMENT = 1")
public class Movie
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private int year;
    private String title;
    private String[] actors;
    
    public Movie()
    {
    
    }
    
    public Movie(int year, String title, String[] actors)
    {
        this.year = year;
        this.title = title;
        this.actors = actors;
    }
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public int getYear()
    {
        return year;
    }
    
    public void setYear(int year)
    {
        this.year = year;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String[] getActors()
    {
        return actors;
    }
    
    public void setActors(String[] actors)
    {
        this.actors = actors;
    }
}
