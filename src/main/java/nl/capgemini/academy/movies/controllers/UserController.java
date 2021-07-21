package nl.capgemini.academy.movies.controllers;

import nl.capgemini.academy.movies.models.User;
import nl.capgemini.academy.movies.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Principal principal) {
        if (principal != null) {
            Optional<User> user = userRepository.findByUsername(principal.getName());
            if (user.isPresent()) {
                return user.get();
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
