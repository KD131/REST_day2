package dtos;

import entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDTO
{
    private Integer id;
    private int year;
    private String title;
    private String[] actors;
    
    public MovieDTO(Movie m)
    {
        if (m.getId() != null)
        {
            this.id = m.getId();
        }
        this.year = m.getYear();
        this.title = m.getTitle();
        this.actors = m.getActors();
    }
    
    public MovieDTO(int year, String title, String[] actors)
    {
        this.year = year;
        this.title = title;
        this.actors = actors;
    }
    
    public static List<MovieDTO> getDtos(List<Movie> entities)
    {
        List<MovieDTO> dtos = new ArrayList<>();
        entities.forEach(m -> dtos.add(new MovieDTO(m)));
        return dtos;
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
