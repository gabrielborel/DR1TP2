package infnet.edu.br.DR1TP2.services;

import infnet.edu.br.DR1TP2.dtos.CreateUserDTO;
import infnet.edu.br.DR1TP2.dtos.UpdateUserDTO;
import infnet.edu.br.DR1TP2.exceptions.EmailAlreadyInUseException;
import infnet.edu.br.DR1TP2.exceptions.UserNotFoundException;
import infnet.edu.br.DR1TP2.models.User;
import infnet.edu.br.DR1TP2.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository = new UserRepository();
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(CreateUserDTO createUserDTO) {
        validateUsername(createUserDTO.username());
        validateEmail(createUserDTO.email());
        if (userRepository.findByEmail(createUserDTO.email()).isPresent()) {
            throw new EmailAlreadyInUseException("Email already in use");
        }
        User user = new User(null, createUserDTO.username(), createUserDTO.email());
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> updateUser(UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(updateUserDTO.id()).orElse(null);
        if (user == null) {
            return Optional.empty();
        }
        if (updateUserDTO.email().isPresent()) {
            String newEmail = updateUserDTO.email().get();
            validateEmail(newEmail);
            boolean emailAlreadyInUse = userRepository.findByEmail(newEmail)
                    .filter(u -> !u.getId().equals(updateUserDTO.id()))
                    .isPresent();
            if (emailAlreadyInUse) {
                throw new EmailAlreadyInUseException("Email already in use");
            }
            user.setEmail(newEmail);
        }
        if (updateUserDTO.username().isPresent()) {
            String newUsername = updateUserDTO.username().get();
            validateUsername(newUsername);
            user.setUsername(newUsername);
        }
        userRepository.save(user);
        return Optional.of(user);
    }

    private void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
    }
}
