package be.iccbxl.pid.reservations_springboot.service;

import be.iccbxl.pid.reservations_springboot.dto.UserCreateDTO;
import be.iccbxl.pid.reservations_springboot.model.User;
import be.iccbxl.pid.reservations_springboot.model.UserRole;
import be.iccbxl.pid.reservations_springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public static class EmailAlreadyUsedException extends RuntimeException {}
    public static class LoginAlreadyUsedException extends RuntimeException {}
    public static class PasswordsDoNotMatchException extends RuntimeException {}

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User getUser(long id) {
        return userRepository.findById(id);
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(long id, User user) {
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    // Méthode d'inscription d'un nouvel utilisateur
    public Long registerUser(UserCreateDTO dto) {

        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            throw new PasswordsDoNotMatchException();
        }
        if (userRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new EmailAlreadyUsedException();
        }
        if (userRepository.existsByLoginIgnoreCase(dto.getLogin())) {
            throw new LoginAlreadyUsedException();
        }

        User u = new User();
        u.setLogin(dto.getLogin());
        u.setFirstname(dto.getFirstname());
        u.setLastname(dto.getLastname());
        u.setEmail(dto.getEmail());
        u.setLangue(dto.getLangue());
        u.setRole(UserRole.MEMBER); // rôle par défaut
        // Hachage BCrypt (stocké dans le champ password)
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        return userRepository.save(u).getId();
    }

    public void updateProfile(Long id, User updated) {
        User user = userRepository.findById(id).orElseThrow();
        user.setFirstname(updated.getFirstname());
        user.setLastname(updated.getLastname());
        user.setEmail(updated.getEmail());
        user.setLangue(updated.getLangue());
        userRepository.save(user);
    }


    public void deleteOwnAccount(Long id) {
        userRepository.deleteById(id);
    }
}
