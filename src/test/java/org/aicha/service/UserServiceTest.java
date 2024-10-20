package org.aicha.service;

import org.aicha.model.User;
import org.aicha.model.enums.UserRole;
import org.aicha.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        assertEquals(Collections.emptyList(), userService.getAllUsers());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById_ValidId() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertEquals(Optional.of(user), userService.getUserById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserById_InvalidId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.getUserById(-1L));
        assertEquals("Invalid id", exception.getMessage());
    }

    @Test
    public void testCreateUser_Success() {
        User newUser = new User("username", "valid.email@example.com", "password", "firstName", "lastName", UserRole.user);
        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(newUser.getUsername())).thenReturn(Optional.empty());
        when(userRepository.create(newUser)).thenReturn(Optional.of(newUser));

        assertEquals(Optional.of(newUser), userService.createUser(newUser));
        verify(userRepository, times(1)).findByEmail(newUser.getEmail());
        verify(userRepository, times(1)).findByUsername(newUser.getUsername());
        verify(userRepository, times(1)).create(newUser);
    }

    @Test
    public void testCreateUser_DuplicateEmail() {
        User newUser = new User("username", "valid.email@example.com", "password", "firstName", "lastName", UserRole.user);
        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.of(newUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(newUser));
        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    public void testCreateUser_DuplicateUsername() {
        User newUser = new User("username", "valid.email@example.com", "password", "firstName", "lastName", UserRole.user);
        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(newUser.getUsername())).thenReturn(Optional.of(newUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(newUser));
        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    public void testUpdateUser_Success() {
        User user = new User("username", "valid.email@example.com", "password", "firstName", "lastName", UserRole.user);
        user.setId(1L);
        when(userRepository.update(user)).thenReturn(Optional.of(user));

        assertEquals(Optional.of(user), userService.updateUser(user));
        verify(userRepository, times(1)).update(user);
    }

    @Test
    public void testUpdateUser_NotFound() {
        User user = new User("username", "valid.email@example.com", "password", "firstName", "lastName", UserRole.user);
        user.setId(1L);
        when(userRepository.update(user)).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), userService.updateUser(user));
    }
    @Test
    public void testGetUserByEmail_Success() {
        User user = new User("username", "valid.email@example.com", "password", "firstName", "lastName", UserRole.user);
        when(userRepository.findByEmail("valid.email@example.com")).thenReturn(Optional.of(user));

        assertEquals(Optional.of(user), userService.getUserByEmail("valid.email@example.com"));
        verify(userRepository, times(1)).findByEmail("valid.email@example.com");
    }

    @Test
    public void testGetUserByEmail_NotFound() {
        when(userRepository.findByEmail("invalid.email@example.com")).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), userService.getUserByEmail("invalid.email@example.com"));
        verify(userRepository, times(1)).findByEmail("invalid.email@example.com");
    }


    @Test
    public void testCreateUser_InvalidUser() {
        User invalidUser = new User("", "invalid.email", "", "firstName", "lastName", UserRole.user);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(invalidUser));
        assertEquals("Invalid user", exception.getMessage());
        verify(userRepository, never()).create(invalidUser);
    }


    @Test
    public void testUpdateUser_InvalidUser() {
        User invalidUser = new User("", "invalid.email", "", "firstName", "lastName", UserRole.user);
        invalidUser.setId(1L);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.updateUser(invalidUser));
        assertEquals("Invalid user", exception.getMessage());
        verify(userRepository, never()).update(invalidUser);
    }




}