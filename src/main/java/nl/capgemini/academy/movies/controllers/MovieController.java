package nl.capgemini.academy.movies.controllers;

import nl.capgemini.academy.movies.models.Movie;
import nl.capgemini.academy.movies.repositories.MovieRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/movie")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
public class MovieController {

    private MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public Iterable<Movie> getAll() {
        return this.movieRepository.findAll();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody Movie movie) {
        this.movieRepository.save(movie);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) {
        this.movieRepository.deleteById(id);
    }

    @RequestMapping(value = "watched/{id}", method = RequestMethod.PUT)
    public void toggleWatched(@PathVariable long id) {
        Movie movie = this.movieRepository.getOne(id);
        movie.setWatched(!movie.isWatched());
        this.movieRepository.save(movie);
    }
}
