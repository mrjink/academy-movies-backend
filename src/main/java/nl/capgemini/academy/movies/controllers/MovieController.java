package nl.capgemini.academy.movies.controllers;

import nl.capgemini.academy.movies.models.Movie;
import nl.capgemini.academy.movies.models.User;
import nl.capgemini.academy.movies.repositories.MovieRepository;
import nl.capgemini.academy.movies.services.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("api/movie")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
public class MovieController {

    private final MovieRepository movieRepository;
    private final UserService userService;

    public MovieController(MovieRepository movieRepository, UserService userService) {
        this.movieRepository = movieRepository;
        this.userService = userService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<Movie> getAll(Principal principal) {
        User user = userService.getUser(principal);
        return movieRepository.findAllByUser(user, Sort.by("title"));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Movie getOne(Principal principal, @PathVariable long id) {
        User user = userService.getUser(principal);
        Optional<Movie> optional = movieRepository.findByUserAndId(user, id);
        return optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void create(Principal principal, @RequestBody Movie movie) {
        User user = userService.getUser(principal);
        movie.setUser(user);
        movieRepository.save(movie);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(Principal principal, @PathVariable long id, @RequestBody Movie movie) {
        User user = userService.getUser(principal);
        Movie currentMovie = movieRepository.findByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        currentMovie.setTitle(movie.getTitle());
        currentMovie.setWatched(movie.isWatched());
        movieRepository.save(currentMovie);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(Principal principal, @PathVariable long id) {
        User user = userService.getUser(principal);
        movieRepository.deleteByUserAndId(user, id);
    }

    @RequestMapping(value = "/watched/{id}", method = RequestMethod.PUT)
    public void toggleWatched(Principal principal, @PathVariable long id) {
        User user = userService.getUser(principal);
        Movie movie = movieRepository.findByUserAndId(user, id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        movie.setWatched(!movie.isWatched());
        movieRepository.save(movie);
    }
}
