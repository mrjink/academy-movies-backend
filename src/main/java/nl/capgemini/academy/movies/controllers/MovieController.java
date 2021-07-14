package nl.capgemini.academy.movies.controllers;

import nl.capgemini.academy.movies.models.Movie;
import nl.capgemini.academy.movies.repositories.MovieRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("api/movie")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<Movie> getAll() {
        return movieRepository.findAll(Sort.by("title"));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Movie getOne(@PathVariable long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void create(@RequestBody Movie movie) {
        movieRepository.save(movie);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable long id, @RequestBody Movie movie) {
        Movie currentMovie = movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        currentMovie.setTitle(movie.getTitle());
        currentMovie.setWatched(movie.isWatched());
        movieRepository.save(currentMovie);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) {
        movieRepository.deleteById(id);
    }

    @RequestMapping(value = "/watched/{id}", method = RequestMethod.PUT)
    public void toggleWatched(@PathVariable long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        movie.setWatched(!movie.isWatched());
        movieRepository.save(movie);
    }
}
