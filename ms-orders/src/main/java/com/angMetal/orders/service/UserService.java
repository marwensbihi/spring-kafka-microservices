package com.angMetal.orders.service;


import com.angMetal.orders.payloads.request.UpdatePasswordDTO;
import com.angMetal.orders.payloads.request.UpdateUserDTO;
import com.angMetal.orders.repositories.UserRepository;
import com.angMetal.orders.enums.ERole;
import com.angMetal.orders.entity.Role;
import com.angMetal.orders.entity.User;
import com.angMetal.orders.payloads.response.Response;
import com.angMetal.orders.repositories.RoleRepository;
import com.angMetal.orders.security.jwt.JwtUtils;
import com.angMetal.orders.utils.ResponseUtils;
import com.angMetal.orders.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserUtils userUtils;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity<Response> getAll() {
        try {
            List<User> users = userRepository.findAll()
                    .stream()
                    .filter(user -> !user.getRoles()
                            .contains(roleRepository.findByName(ERole.ROLE_ADMIN)))
                    .map(user -> {
                        user.setPassword(null);
                        return user;
                    })
                    .collect(Collectors.toList());

            HashMap<String, List<User>> data = new HashMap<>();
            data.put("users", users);

            return ResponseUtils.handleResponse("All users retrieved successfully", HttpStatus.OK, data);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Response> getById(Long userId) {
        try {
            User user = userRepository.findById(userId).map(u -> {
                u.setPassword(null);
                return u;
            }).orElseThrow(() -> new NoSuchElementException("User with id: '" + userId + "' does not exist."));

            HashMap<String, User> data = new HashMap<>();
            data.put("user", user);

            return ResponseUtils.handleResponse("User: '" + userId + "' retrieved successfully", HttpStatus.OK, data);
        } catch (NoSuchElementException e) {
            return ResponseUtils.handleException(e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Response> deleteById(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User with id: '" + userId + "' does not exist"));

            user.getRoles().removeAll(user.getRoles());

            userRepository.deleteById(userId);

            return ResponseUtils.handleResponse("User: '" + userId + "' deleted successfully", HttpStatus.OK, null);
        } catch (NoSuchElementException e) {
            return ResponseUtils.handleException(e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<Response> update(Long userId, UpdateUserDTO updateUserDTO) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User with id: '" + userId + "' does not exist"));

            String username = updateUserDTO.getUsername();
            String email = updateUserDTO.getEmail();
            String firstName = updateUserDTO.getFirstName();
            String lastName = updateUserDTO.getLastName();
            String address = updateUserDTO.getAddress();
            String phoneNumber = updateUserDTO.getPhoneNumber();

            if (username != null && !username.equals(user.getUsername()) && userUtils.isUsernameValid(username)) {
                user.setUsername(username);
            }

            if (email != null && !email.equals(user.getEmail()) && userUtils.isEmailValid(email)) {
                user.setEmail(email);
            }

            if (firstName != null && !firstName.isEmpty()) {
                user.setFirstName(firstName);
            }

            if (lastName != null && !lastName.isEmpty()) {
                user.setLastName(lastName);
            }

            if (address != null && !address.isEmpty()) {
                user.setAddress(address);
            }

            if (phoneNumber != null && !phoneNumber.isEmpty() && userUtils.isPhoneNumberValid(phoneNumber)) {
                user.setPhoneNumber(phoneNumber);
            }

            userRepository.save(user);
            String token = jwtUtils.generateJwtTokenFromUsername(username);

            HashMap<String, String> data = new HashMap<>();
            data.put("accessToken", token);

            return ResponseUtils.handleResponse("User updated successfully", HttpStatus.OK, data);
        } catch (NoSuchElementException e) {
            return ResponseUtils.handleException(e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<Response> updatePassword(Long userId, UpdatePasswordDTO updatePasswordDTO) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User with id: '" + userId + "' does not exist"));

            String oldPassword = updatePasswordDTO.getOldPassword();
            String newPassword = updatePasswordDTO.getNewPassword();

            if (oldPassword == null || oldPassword.isEmpty()) {
                throw new IllegalStateException("Old password cannot be null/empty");
            }

            if (newPassword == null || newPassword.isEmpty()) {
                throw new IllegalStateException("New password cannot be null/empty");
            }

            if (!encoder.matches(oldPassword, user.getPassword())) {
                throw new IllegalStateException("Passwords do not match");
            }

            if (userUtils.isPasswordValid(newPassword)) {
                user.setPassword(encoder.encode(newPassword));
            }

            return ResponseUtils.handleResponse("Password updated successfully", HttpStatus.OK, null);
        } catch (NoSuchElementException e) {
            return ResponseUtils.handleException(e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<Response> updatePasswordByAdmin(Long userId, String newPassword) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User with id: '" + userId + "' does not exist"));

            if (newPassword == null || newPassword.isEmpty()) {
                throw new IllegalStateException("New password cannot be null/empty");
            }

            if (userUtils.isPasswordValid(newPassword)) {
                user.setPassword(encoder.encode(newPassword));
            }

            return ResponseUtils.handleResponse("Password updated successfully", HttpStatus.OK, null);
        } catch (NoSuchElementException e) {
            return ResponseUtils.handleException(e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Response> assignRole(Long userId, String roleToAdd) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User with id: '" + userId + "' does not exist"));

            ERole eRole;

            switch (roleToAdd) {
                case "ADMIN":
                    eRole = ERole.ROLE_ADMIN;
                    break;
                case "USER":
                    eRole = ERole.ROLE_USER;
                    break;
                default:
                    throw new NoSuchElementException("Permission: '" + roleToAdd.toUpperCase() + "' does not exist");
            }

            Role role = roleRepository.findByName(eRole);

            user.getRoles().add(role);
            userRepository.save(user);

            return ResponseUtils.handleResponse("Role: '" + roleToAdd.toUpperCase() + "' assigned successfully", HttpStatus.OK, null);
        } catch (NoSuchElementException e) {
            return ResponseUtils.handleException(e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Response> revokeRole(Long userId, String roleToRevoke) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User with id: '" + userId + "' does not exist"));

            ERole eRole;

            switch (roleToRevoke) {
                case "ADMIN":
                    eRole = ERole.ROLE_ADMIN;
                    break;
                case "USER":
                    eRole = ERole.ROLE_USER;
                    break;
                default:
                    throw new NoSuchElementException("Role: '" + roleToRevoke.toUpperCase() + "' does not exist");
            }

            Role role = roleRepository.findByName(eRole);

            user.getRoles().remove(role);
            userRepository.save(user);

            return ResponseUtils.handleResponse("Role: '" + roleToRevoke.toUpperCase() + "' revoked successfully", HttpStatus.OK, null);
        } catch (NoSuchElementException e) {
            return ResponseUtils.handleException(e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtils.handleException(e, HttpStatus.BAD_REQUEST);
        }
    }
}