package services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import repositories.UserRepository;
import models.User;
import dtos.UserDTO;
import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setUser_email(user.getUser_email());
        newUser.setUser_password_hash(user.getUser_password_hash());
        newUser.setUser_leetcoins(0);

        // Check if username or email already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByUser_email(user.getUser_email()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(user);
    }

    public User updateUser(Integer id, User user) {
        User existingUser = getUserById(id);
        // if data is not provided, stay with existing data
        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }else{
            existingUser.setUsername(existingUser.getUsername());
        }

        if (user.getUser_email() != null) {
            existingUser.setUser_email(user.getUser_email());
        }else{
            existingUser.setUser_email(existingUser.getUser_email());
        }

        if (user.getUser_password_hash() != null) {
            existingUser.setUser_password_hash(user.getUser_password_hash());
        }else{
            existingUser.setUser_password_hash(existingUser.getUser_password_hash());
        }

        return userRepository.save(existingUser);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
        // TODO: message if user is deleted correctly
        // TODO: delete all records associated with the user
    }

}
