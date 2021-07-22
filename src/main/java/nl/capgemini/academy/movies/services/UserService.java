package nl.capgemini.academy.movies.services;

import nl.capgemini.academy.movies.models.User;
import nl.capgemini.academy.movies.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
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
